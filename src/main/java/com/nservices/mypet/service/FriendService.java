package com.nservices.mypet.service;

import com.nservices.mypet.dto.FriendDto;
import com.nservices.mypet.entity.FriendEntity;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.mapper.FriendMapper;
import com.nservices.mypet.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final UserService userService;
    private final FriendRepository friendRepository;

    private final FriendMapper friendMapper;

    public void addUnconfirmedFriend(String friend_username, String username) {
        User friend_user = userService.getUserByUsername(friend_username);
        User user = userService.getUserByUsername(username);
        FriendEntity friend = new FriendEntity(user, friend_user, 0);
        friendRepository.save(friend);
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
}
