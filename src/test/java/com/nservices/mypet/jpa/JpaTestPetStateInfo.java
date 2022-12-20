package com.nservices.mypet.jpa;

import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.PetEntity;
import com.nservices.mypet.entity.PetStateInfoEntity;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.model.PetState;
import com.nservices.mypet.repository.OwnerRepository;
import com.nservices.mypet.repository.PetRepository;
import com.nservices.mypet.repository.PetStateInfoRepository;
import com.nservices.mypet.repository.UserRepository;
import com.nservices.mypet.repository.security.RoleConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
public class JpaTestPetStateInfo {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetStateInfoRepository petStateInfoRepository;

    private User user;

    @BeforeEach
    public void init() {
        user = new User("nik", "1", 1, RoleConstants.ROLE_USER);
    }

    @Test
    public void testfindByPetIdAndState() {
        userRepository.save(user);
        OwnerEntity owner = new OwnerEntity("nik_owner", "nik@gmail.com", user);
        ownerRepository.save(owner);
        PetEntity pet = new PetEntity("pet-1", 0, LocalDateTime.now(), owner);
        petRepository.save(pet);

        PetStateInfoEntity petStateInfo = petStateInfoRepository.save(
                new PetStateInfoEntity(pet, PetState.HUNGRY, LocalDateTime.now(), LocalDateTime.now(), 0, 1)
        );

        Assertions.assertThat(petStateInfoRepository.findByPetIdAndState(pet.getId(), PetState.HUNGRY))
                .isNotNull();
    }
}
