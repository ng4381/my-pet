package com.nservices.mypet.service;

import com.nservices.mypet.dto.FriendDto;
import com.nservices.mypet.entity.FriendEntity;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.exception.FriendAlreadyExists;
import com.nservices.mypet.mapper.FriendMapper;
import com.nservices.mypet.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<FriendDto> getUserFriendsDto(String username) {

        List<FriendEntity> fr = friendRepository.findAllByUserUsername(username);
        for (FriendEntity fe : fr) {
            System.out.println(fe);
            friendMapper.EntityToObject(fe);
        }

        System.out.println("=====================================");

        return friendRepository.findAllByUserUsername(username).stream()
                .map(friendEntity -> friendMapper.EntityToObject(friendEntity)).collect(Collectors.toList());
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
