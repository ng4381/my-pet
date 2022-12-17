package com.nservices.mypet.repository;

import com.nservices.mypet.entity.FriendEntity;
import com.nservices.mypet.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.nservices.mypet.repository.security.RoleConstants.ROLE_USER;

@DataJpaTest
@Slf4j
class FriendRepositoryTest {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;


    private final String USER1_NAME = "TEST_USER_1";
    private final String USER1_PASSWORD = "password";

    private final String USER2_NAME = "TEST_USER_2";
    private final String USER2_PASSWORD = "password";

    private final String USER3_NAME = "TEST_USER_3";
    private final String USER3_PASSWORD = "password";

    private User user1, user2, user3;
    private FriendEntity friend1, friend2;

    @BeforeEach
    private void init() {
        user1 = userRepository.save(new User(USER1_NAME, USER1_PASSWORD, 1, ROLE_USER));
        user2 = userRepository.save(new User(USER2_NAME, USER2_PASSWORD, 1, ROLE_USER));
        user3 = userRepository.save(new User(USER3_NAME, USER3_PASSWORD, 1, ROLE_USER));
        FriendEntity friend1 = friendRepository.save(new FriendEntity(user1, user2, 0));
        FriendEntity friend2 = friendRepository.save(new FriendEntity(user1, user3, 0));
    }

    @Test
    public void shouldSaveFriend() {
        Assertions.assertThat(friend1).extracting("user.username", "friend.username", "confirmed")
                .contains(USER1_NAME, USER2_NAME, 0);
        Assertions.assertThat(friend2).extracting("user.username", "friend.username", "confirmed")
                .contains(USER1_NAME, USER3_NAME, 0);
    }

    @Test
    public void shouldReturnAllFriends() {
        List<FriendEntity> friends = friendRepository.findAllByUserUsername(USER1_NAME);
        friends.forEach(friend -> System.out.println("User: " + friend.getUser() + " Friend: " + friend.getFriend()));
        Assertions.assertThat(friends).isNotEmpty();
    }
}