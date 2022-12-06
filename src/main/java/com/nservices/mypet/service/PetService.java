package com.nservices.mypet.service;

import com.nservices.mypet.dto.PetDTO;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.PetEntity;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.entity.PetStatesMementoEntity;
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

        // 3. Add state OK
        PetStatesMementoEntity petStatesMemento = new PetStatesMementoEntity(pet, List.of(), LocalDateTime.now());
        petStatesMementoService.savePetStatesMemento(petStatesMemento);

        // 4. Add pet state info
        PetStateInfoEntity petStateInfoHungry = new PetStateInfoEntity(pet, PetState.HUNGRY, LocalDateTime.now(), LocalDateTime.now(), 0, 0);
        petStateInfoService.savePetStateInfo(petStateInfoHungry);

        PetStateInfoEntity petStateInfoSick = new PetStateInfoEntity(pet, PetState.SICK, LocalDateTime.now(), LocalDateTime.now(), 0, 0);
        petStateInfoService.savePetStateInfo(petStateInfoSick);

        PetStateInfoEntity petStateInfoBored = new PetStateInfoEntity(pet, PetState.BORED, LocalDateTime.now(), LocalDateTime.now(), 0, 0);
        petStateInfoService.savePetStateInfo(petStateInfoBored);

        PetStateInfoEntity petStateInfoDirty = new PetStateInfoEntity(pet, PetState.DIRTY, LocalDateTime.now(), LocalDateTime.now(), 0, 0);
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
}