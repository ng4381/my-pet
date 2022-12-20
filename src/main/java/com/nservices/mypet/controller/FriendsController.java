package com.nservices.mypet.controller;

import com.nservices.mypet.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    @Autowired
    private FriendService friendsService;

    @PostMapping("/{username}")
    public ResponseEntity<HttpStatus> createFriendToConfirm(@PathVariable("username") String username, Principal principal) {
        friendsService.addUnconfirmedFriend(username, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @GetMapping
//    public List<FriendDto> getAllUserFriends(Principal principal) {
//        return friendsService.getUserFriendsDto(principal.getName());
//    }

    @PostMapping("/confirm/{username}")
    public ResponseEntity<HttpStatus> confirmFriend(@PathVariable("username") String friend_username, Principal principal) {
        friendsService.confirmFriend(friend_username, principal.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<HttpStatus> deleteFriends(@PathVariable("username") String friend_username, Principal principal) {
        friendsService.deleteFriendByUserUsernameFriendUsername(principal.getName(), friend_username);
        return ResponseEntity.ok().build();
    }
}
