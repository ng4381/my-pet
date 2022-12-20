package com.nservices.mypet.service;

import com.nservices.mypet.dto.UserDto;
import com.nservices.mypet.entity.FriendEntity;
import com.nservices.mypet.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.nservices.mypet.util.Constants.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FriendsServiceTest {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @BeforeEach
    private void init() {

    }

    @Test
    public void shouldSaveUnconfirmedFriend() {

        addUnconfirmedFriend();

        List<FriendEntity> user1_friends = friendService.getUserFriendsByUsername(USER1_NAME);
        Assertions.assertThat(user1_friends).filteredOn("user.username", USER1_NAME).filteredOn("friend.username", USER2_NAME).hasSize(1);
    }

    @Test
    public void shouldReturnAllFriends() {
        /*
        User user1 = new User("USER_1", "", 1, ROLE_USER);
        User user2 = new User("USER_2", "", 1, ROLE_USER);
        User user3 = new User("USER_3", "", 1, ROLE_USER);

        given(friendMapper.EntityToObject(any())).willCallRealMethod();

        given(friendRepository.findAllByUserUsername("USER_1"))
                .willReturn(
                        List.of(
                                new FriendEntity(user1, user2, 0),
                                new FriendEntity(user1, user3, 0)
                        )
                );
        List<FriendDto> friendDtoList = friendService.getUserFriendsDto(USER1_NAME);
        friendDtoList.forEach(System.out::println);

        Assertions.assertThat(friendDtoList)
                .filteredOn("username", "USER_1")
                .hasSize(2);


         */
        Assertions.fail("not implemented");
    }

    @Test
    public void throwsExceptionIfFriendAlreadyAdded() {
        addUnconfirmedFriend();
        Assertions.assertThatThrownBy(() -> friendService.addUnconfirmedFriend(USER2_NAME, USER1_NAME));
    }

    @Test
    public void shouldDeleteConfirmedFriend() {
        addAndConfirmUser();
        friendService.deleteFriendByUserUsernameFriendUsername(USER1_NAME, USER2_NAME);
        FriendEntity friend1 = friendService.getFriendByUserAndFriend(USER1_NAME, USER2_NAME);
        FriendEntity friend2 = friendService.getFriendByUserAndFriend(USER2_NAME, USER1_NAME);

        Assertions.assertThat(friend1).isNull();
        Assertions.assertThat(friend2).isNull();
    }

    @Test
    public void shouldDeleteUnconfirmedFriend() {
        addUnconfirmedFriend();
        friendService.deleteFriendByUserUsernameFriendUsername(USER1_NAME, USER2_NAME);
        FriendEntity friend1 = friendService.getFriendByUserAndFriend(USER1_NAME, USER2_NAME);
        FriendEntity friend2 = friendService.getFriendByUserAndFriend(USER2_NAME, USER1_NAME);

        Assertions.assertThat(friend1).isNull();
        Assertions.assertThat(friend2).isNull();
    }

    @Test
    public void shouldConfirmFriend() {
        addAndConfirmUser();

        List<FriendEntity> user1_friends = friendService.getUserFriendsByUsername(USER1_NAME);
        List<FriendEntity> user2_friends = friendService.getUserFriendsByUsername(USER2_NAME);

        Assertions.assertThat(user1_friends).filteredOn("user.username", USER1_NAME).filteredOn("friend.username", USER2_NAME).hasSize(1);
        Assertions.assertThat(user2_friends).filteredOn("user.username", USER2_NAME).filteredOn("friend.username", USER1_NAME).hasSize(1);
    }

    private void addUnconfirmedFriend() {
        userService.saveUser(new UserDto(USER1_NAME, USER1_PASSWORD));
        userService.saveUser(new UserDto(USER2_NAME, USER2_PASSWORD));
        friendService.addUnconfirmedFriend(USER2_NAME, USER1_NAME);
    }


    private void addAndConfirmUser() {
        addUnconfirmedFriend();
        friendService.confirmFriend(USER2_NAME, USER1_NAME);
    }


}