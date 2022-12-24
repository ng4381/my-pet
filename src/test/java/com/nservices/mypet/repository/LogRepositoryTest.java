package com.nservices.mypet.repository;

import com.nservices.mypet.dto.ILogDto;
import com.nservices.mypet.entity.LogEntity;
import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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

    @BeforeEach
    private void init() {
        User user = new User(USER1_NAME, USER1_PASSWORD, 1, ROLE_USER);
        userRepository.save(user);
        OwnerEntity owner = new OwnerEntity(USER1_NAME, EMAIL1, user);
        ownerRepository.save(owner);
        LogEntity logEntity1 = new LogEntity(owner, "test message");
        logRepository.save(logEntity1);
        LogEntity logEntity2 = new LogEntity(owner, "test message");
        logRepository.save(logEntity2);
    }

    @Test
    public void shouldSaveLog() {
        Assertions.assertThat(logRepository.findAll()).hasSize(2);
    }

    @Test
    public void shouldReturnAllLogsByUsername() {

        List<ILogDto> logs = logRepository.findAllLogsByUsername(USER1_NAME);
        Assertions.assertThat(logs).hasSize(2);
    }
}