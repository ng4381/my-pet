package com.nservices.mypet.service;

import com.nservices.mypet.dto.FriendDto;
import com.nservices.mypet.dto.IFriendDto;
import com.nservices.mypet.entity.FriendEntity;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.exception.FriendAlreadyExists;
import com.nservices.mypet.mapper.FriendMapper;
import com.nservices.mypet.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.nservices.mypet.util.Constants.*;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserService userService;
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;

    public void addUnconfirmedFriend(String friend_username, String username) {
        User friend_user = userService.getUserByUsername(friend_username);
        User user = userService.getUserByUsername(username);
        if(isFriendExists(username, friend_username)){
            throw new FriendAlreadyExists("Friend already exists. User: " + username + " Friend: " + friend_username);
        }
        FriendEntity friend = new FriendEntity(user, friend_user, 0);
        friendRepository.save(friend);
    }

    public FriendEntity getFriendByUserAndFriend(String username, String friend_username) {
        User user = userService.getUserByUsername(username);
        User user_friend = userService.getUserByUsername(friend_username);

        return friendRepository.findFriendByUserUsernameAndFriendUsername(username, friend_username);
    }

    /**
     *
     * @param username
     * @return list of friends with statuses (WAITING_FRIEND_CONFIRMATION, CONFIRMED, UNCONFIRMED_FRIEND_REQUEST)
     */
    public List<FriendDto> getUserFriendsDto(String username) {
        List<IFriendDto> friendsConfirmedAndWaitingConfirmation = friendRepository.findAllFriendsWithStatusConfirmedAndWaitingConfirmation(username);
        List<IFriendDto> friendsUnconfirmedFriendRequest = friendRepository.findAllFriendsWithStatusUnconfirmedFriendRequest(username);

        Stream<FriendDto> friendsConfirmedAndWaitingConfirmationDto = friendsConfirmedAndWaitingConfirmation.stream()
                .map(iFriendDto ->convertToFriendDto(iFriendDto, _iFriendDto -> _iFriendDto.getStatus() == 0 ? WAITING_FRIEND_CONFIRMATION : CONFIRMED)
                );

        Stream<FriendDto> friendsUnconfirmedFriendRequestDto = friendsUnconfirmedFriendRequest.stream()
                .map(iFriendDto -> convertToFriendDto(iFriendDto, _iFriendDto -> UNCONFIRMED_FRIEND_REQUEST)
                );

        return Stream.concat(friendsConfirmedAndWaitingConfirmationDto, friendsUnconfirmedFriendRequestDto).collect(Collectors.toList());
    }

    private FriendDto convertToFriendDto(IFriendDto iFriendDto, Function<IFriendDto, String> operator) {
        return new FriendDto(
                iFriendDto.getFriendUsername(),
                operator.apply(iFriendDto),
                iFriendDto.getPetName(),
                iFriendDto.getPetAge(),
                iFriendDto.getFriendshipScore(),
                iFriendDto.getPersonalScore(),
                iFriendDto.getTotalScore(),
                new ArrayList<>());
    }
    public void confirmFriend(String friend_username, String username) {
        User user = userService.getUserByUsername(username);
        User user_friend = userService.getUserByUsername(friend_username);

        // Add current user to a list of friends of the friend
        FriendEntity recordForFriend = new FriendEntity(user_friend, user, 1);

        // Update user record for this friend. Set status to confirmed = 1
        FriendEntity recordForUser = getFriendByUserAndFriend(username, friend_username);

        friendRepository.saveAll(List.of(recordForFriend, recordForUser));
    }

    public void deleteFriendByUserUsernameFriendUsername(String username, String friend_username) {
        //Find both records for user and friend and delete it
        FriendEntity recordForUser = getFriendByUserAndFriend(username, friend_username);
        FriendEntity recordForFriend = getFriendByUserAndFriend(friend_username, username);

        friendRepository.deleteAllInBatch(
                Stream.of(recordForUser, recordForFriend).filter(Objects::nonNull)
                .collect(Collectors.toList())
        );
    }

    public List<FriendEntity> getUserFriendsByUsername(String username) {
        return friendRepository.findAllByUserUsername(username);
    }

    public boolean isFriendExists(String username, String friend_username) {
        return getFriendByUserAndFriend(username, friend_username) != null;
    }
}
