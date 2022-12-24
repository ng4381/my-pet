package com.nservices.mypet.service;

import com.nservices.mypet.dto.PetDTO;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.PetEntity;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.entity.PetStatesMementoEntity;
import com.nservices.mypet.exception.OnlyFriendCanChangeStateException;
import com.nservices.mypet.exception.OnlyUserCanChangeStateException;
import com.nservices.mypet.exception.PetNotFoundException;
import com.nservices.mypet.exception.UserAlreadyHasAPetException;
import com.nservices.mypet.model.PetState;
import com.nservices.mypet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;

    private final OwnerService ownerService;

    private final PetStatesMementoService petStatesMementoService;
    private final PetStateInfoService petStateInfoService;

    @Transactional
    public void savePet(PetDTO petDTO, String username) {

        // check if pet already exists. One pet per owner!
        if (petRepository.findByUsername(username) != null) {
            throw new UserAlreadyHasAPetException("User(" + username + ") already has a pet");
        }

        // 1. Calculate age
        int age = calculateAge(petDTO.getBirthDate());

        // 2. Add pet
        OwnerEntity owner = ownerService.getOwner(username);
        PetEntity pet = new PetEntity(petDTO.getName(), age, petDTO.getBirthDate(), owner);
        petRepository.save(pet);

        LocalDateTime petBirthday = petDTO.getBirthDate();

        // 3. Add state OK
        PetStatesMementoEntity petStatesMemento = new PetStatesMementoEntity(pet, List.of(), petBirthday);
        petStatesMementoService.savePetStatesMemento(petStatesMemento);

        // 4. Add pet state info
        PetStateInfoEntity petStateInfoHungry = new PetStateInfoEntity(pet, PetState.HUNGRY, petBirthday, petBirthday, 0, 0);
        petStateInfoService.savePetStateInfo(petStateInfoHungry);

        PetStateInfoEntity petStateInfoSick = new PetStateInfoEntity(pet, PetState.SICK, petBirthday, petBirthday, 0, 0);
        petStateInfoService.savePetStateInfo(petStateInfoSick);

        PetStateInfoEntity petStateInfoBored = new PetStateInfoEntity(pet, PetState.BORED, petBirthday, petBirthday, 0, 0);
        petStateInfoService.savePetStateInfo(petStateInfoBored);

        PetStateInfoEntity petStateInfoDirty = new PetStateInfoEntity(pet, PetState.DIRTY, petBirthday, petBirthday, 0, 0);
        petStateInfoService.savePetStateInfo(petStateInfoDirty);


    }

    private int calculateAge(LocalDateTime birthDate) {
        return LocalDateTime.now().compareTo(birthDate);
    }

    public PetEntity getPet(String username) {
        PetEntity pet = petRepository.findByUsername(username);
        if (pet == null) {
            throw new PetNotFoundException("Pet for user (" + username + ") not found");
        }
        return pet;
    }

    public List<PetEntity> getAllAlivePets() {
        return petRepository.findAll();
    }

    /**
     * Resets user pet state
     *
     * @param username
     * @param state
     */
    public void resetState(String username, String state) {
        PetEntity pet = getPet(username);
        PetStateInfoEntity petStateInfo = petStateInfoService.getPetStateInfo(pet.getId(), PetState.valueOf(state.toUpperCase()));
        if(petStateInfo.getFriendOnly() == 1) {
            throw new OnlyFriendCanChangeStateException("Only friend can change the state " + state);
        }
        resetPetStateInfo(petStateInfo);
        petStateInfoService.savePetStateInfo(petStateInfo);
    }

    /**
     * Resets friend pet state
     *
     * @param friend_username
     * @param state
     */
    public void resetFriendsState(String friend_username, String state) {
        PetEntity pet = getPet(friend_username);
        PetStateInfoEntity petStateInfo = petStateInfoService.getPetStateInfo(pet.getId(), PetState.valueOf(state.toUpperCase()));
        if(petStateInfo.getFriendOnly() == 0) {
            throw new OnlyUserCanChangeStateException("Only user(owner) can change the state " + state);
        }
        resetPetStateInfo(petStateInfo);
        petStateInfoService.savePetStateInfo(petStateInfo);
    }

    private void resetPetStateInfo(PetStateInfoEntity petStateInfo) {
        petStateInfo.setActive(0);
        petStateInfo.setLastModification(LocalDateTime.now());
        petStateInfo.setStart(LocalDateTime.now());
        petStateInfo.setMinutes(0);
        petStateInfo.setFriendOnly(0);
        petStateInfo.setStart(LocalDateTime.of(0,1,1,0,0));
    }
}