package com.nservices.mypet.controller;

import com.nservices.mypet.dto.FriendDto;
import com.nservices.mypet.service.FriendService;
import com.nservices.mypet.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/friends")
@Slf4j
public class FriendsController {

    @Autowired
    private FriendService friendsService;

    @Autowired
    private PetService petService;


    @PostMapping("/{username}")
    public ResponseEntity<HttpStatus> createFriendToConfirm(@PathVariable("username") String username, Principal principal) {
        friendsService.addUnconfirmedFriend(username, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<FriendDto> getAllUserFriends(Principal principal) {
        return friendsService.getUserFriendsDto(principal.getName());
    }

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

    @PostMapping("/states/reset/{username}/{state}")
    public ResponseEntity<HttpStatus> resetFriendsPetState(@PathVariable("username") String friend_username, @PathVariable("state") String state, Principal principal) {
        log.info("Principal: " + principal);
        petService.resetFriendsState(friend_username, state);
        return ResponseEntity.ok().build();
    }

}
