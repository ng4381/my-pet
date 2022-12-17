package com.nservices.mypet.service;

import com.nservices.mypet.dto.FriendDto;
import com.nservices.mypet.entity.FriendEntity;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.mapper.FriendMapper;
import com.nservices.mypet.repository.FriendRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.nservices.mypet.repository.security.RoleConstants.ROLE_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FriendsServiceTest {
    @Mock
    private FriendRepository friendRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private FriendService friendService;


    @Mock
    private FriendMapper friendMapper;

    private final String USER1_NAME = "USER_1";
    private final String USER1_PASSWORD = "password";

    private final String USER2_NAME = "USER_2";
    private final String USER2_PASSWORD = "password";

    @Test
    public void shouldSaveUnconfirmedFriend() {
        given(friendRepository.save(any(FriendEntity.class))).willReturn(any(FriendEntity.class));
        friendService.addUnconfirmedFriend(USER1_NAME, USER2_NAME);
        verify(friendRepository).save(any(FriendEntity.class));
    }

    @Test
    public void shouldReturnAllFriends() {
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


    }
}