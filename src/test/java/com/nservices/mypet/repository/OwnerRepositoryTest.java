package com.nservices.mypet.repository;

import com.nservices.mypet.entity.OwnerEntity;
import com.nservices.mypet.entity.Score;
import com.nservices.mypet.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.nservices.mypet.util.TestConstants.*;

@DataJpaTest
class OwnerRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;


    @Test
    public void shouldSaveOwnerWithScore() {
        User user = userRepository.save(new User(
                USER1_NAME, USER1_PASSWORD, 1, ""
        ));

        userRepository.save(user);
        OwnerEntity owner = OwnerEntity.builder()
                .name(OWNER1_NAME)
                .score(new Score(100, 500, 600))
                .user(user).build();
        ownerRepository.save(owner);

        Assertions.assertThat(ownerRepository.findById(owner.getId()).get())
                .hasFieldOrPropertyWithValue("score.friendshipScore", 100)
                .hasFieldOrPropertyWithValue("score.personalScore", 500)
                .hasFieldOrPropertyWithValue("score.totalScore", 600);
    }
}