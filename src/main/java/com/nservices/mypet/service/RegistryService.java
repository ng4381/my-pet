package com.nservices.mypet.service;

import com.nservices.mypet.dto.OwnerDTO;
import com.nservices.mypet.dto.PetDTO;
import com.nservices.mypet.dto.RegistryInfoDTO;
import com.nservices.mypet.dto.UserDto;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.mapper.OwnerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistryService {
    private final UserService userService;
    private final OwnerService ownerService;
    private final PetService petService;
    private final OwnerMapper ownerMapper;

    @Transactional
    public void registerPetAndUser(RegistryInfoDTO regInfo) {
        User user = userService.saveUser(new UserDto(regInfo.getUsername(), regInfo.getPassword()));
        OwnerEntity owner = ownerService.saveOwner(new OwnerDTO(0L, regInfo.getOwnerName(), regInfo.getEmail()), user.getUsername());
        petService.savePet(new PetDTO(0L, regInfo.getPetName(), 0, LocalDateTime.now()), user.getUsername());
    }
}
