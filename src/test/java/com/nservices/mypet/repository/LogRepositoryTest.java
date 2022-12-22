package com.nservices.mypet.repository;

import com.nservices.mypet.entity.LogEntity;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.nservices.mypet.repository.security.RoleConstants.ROLE_USER;
import static com.nservices.mypet.util.TestConstants.*;

@DataJpaTest
class LogRepositoryTest {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    public void shouldSaveLog() {
        User user = new User(USER1_NAME, USER1_PASSWORD, 1, ROLE_USER);
        userRepository.save(user);
        OwnerEntity owner = new OwnerEntity(USER1_NAME, EMAIL1, user);
        ownerRepository.save(owner);
        LogEntity logEntity1 = new LogEntity(owner, "test message");
        logRepository.save(logEntity1);
        LogEntity logEntity2 = new LogEntity(owner, "test message");
        logRepository.save(logEntity2);

        Assertions.assertThat(logRepository.findById(logEntity1.getId())).isNotNull();
        Assertions.assertThat(logRepository.findById(logEntity2.getId())).isNotNull();
        Assertions.assertThat(logRepository.findAll()).hasSize(2);
        Assertions.assertThat(userRepository.findAll()).hasSize(1);
        Assertions.assertThat(ownerRepository.findAll()).hasSize(1);
    }
}