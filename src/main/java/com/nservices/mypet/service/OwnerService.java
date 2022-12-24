package com.nservices.mypet.service;

import com.nservices.mypet.dto.OwnerDTO;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.exception.UserAlreadyHasAnOwnerException;
import com.nservices.mypet.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final UserService userService;
    public OwnerEntity saveOwner(OwnerDTO ownerDTO, String username) {
        // One User one Owner
        if(getOwner(username) != null) {
            throw new UserAlreadyHasAnOwnerException("Only one user per owner. User(" + username + ") already has an owner");
        }
        User user = userService.getUserByUsername(username);
        OwnerEntity owner = new OwnerEntity(ownerDTO.getName(), ownerDTO.getEmail(), user);
        return ownerRepository.save(owner);
    }

    public OwnerEntity save(OwnerEntity owner) {
        return ownerRepository.save(owner);
    }

    public OwnerEntity getOwner(String username) {
        return ownerRepository.findByUsername(username);
    }
}
