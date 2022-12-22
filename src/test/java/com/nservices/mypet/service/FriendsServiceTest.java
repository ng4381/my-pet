package com.nservices.mypet.service;

import com.nservices.mypet.dto.FriendDto;
import com.nservices.mypet.dto.UserDto;
import com.nservices.mypet.entity.FriendEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.nservices.mypet.util.TestConstants.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FriendsServiceTest {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    public void initTest() {
        userService.saveUser(new UserDto(USER1_NAME, ""));
        userService.saveUser(new UserDto(USER2_NAME, ""));
        userService.saveUser(new UserDto(USER3_NAME, ""));
        userService.saveUser(new UserDto(USER4_NAME, ""));
    }

    @Test
    @Order(2)
    public void shouldSaveUnconfirmedFriend() {
        friendService.addUnconfirmedFriend(USER2_NAME, USER1_NAME);

        List<FriendEntity> user1_friends = friendService.getUserFriendsByUsername(USER1_NAME);
        Assertions.assertThat(user1_friends).filteredOn("user.username", USER1_NAME).filteredOn("friend.username", USER2_NAME).hasSize(1);
    }

    @Test
    @Order(3)
    public void shouldConfirmFriend() {
        friendService.confirmFriend(USER2_NAME, USER1_NAME);

        List<FriendEntity> user1_friends = friendService.getUserFriendsByUsername(USER1_NAME);
        List<FriendEntity> user2_friends = friendService.getUserFriendsByUsername(USER2_NAME);

        Assertions.assertThat(user1_friends).filteredOn("user.username", USER1_NAME).filteredOn("friend.username", USER2_NAME).hasSize(1);
        Assertions.assertThat(user2_friends).filteredOn("user.username", USER2_NAME).filteredOn("friend.username", USER1_NAME).hasSize(1);
    }

    @Test
    @Order(4)
    public void shouldReturnAllFriends() {

        List<FriendDto> friendDtoList = friendService.getUserFriendsDto(USER1_NAME);
        Assertions.assertThat(friendDtoList).isNotEmpty();
    }

    @Test
    @Order(5)
    public void throwsExceptionIfFriendAlreadyAdded() {
        Assertions.assertThatThrownBy(() -> friendService.addUnconfirmedFriend(USER2_NAME, USER1_NAME));
    }

    @Test
    @Order(6)
    public void shouldDeleteConfirmedFriend() {
        friendService.deleteFriendByUserUsernameFriendUsername(USER1_NAME, USER2_NAME);
        FriendEntity friend1 = friendService.getFriendByUserAndFriend(USER1_NAME, USER2_NAME);
        FriendEntity friend2 = friendService.getFriendByUserAndFriend(USER2_NAME, USER1_NAME);

        Assertions.assertThat(friend1).isNull();
        Assertions.assertThat(friend2).isNull();
    }

    @Test
    @Order(7)
    public void shouldDeleteUnconfirmedFriend() {

        friendService.addUnconfirmedFriend(USER3_NAME, USER1_NAME);
        friendService.deleteFriendByUserUsernameFriendUsername(USER1_NAME, USER3_NAME);
        FriendEntity friend1 = friendService.getFriendByUserAndFriend(USER1_NAME, USER3_NAME);
        FriendEntity friend2 = friendService.getFriendByUserAndFriend(USER3_NAME, USER1_NAME);

        Assertions.assertThat(friend1).isNull();
        Assertions.assertThat(friend2).isNull();
    }
}