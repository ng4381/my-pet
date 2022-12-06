package com.nservices.mypet.controller;

import com.nservices.mypet.dto.UserDto;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    public void createUser(@RequestBody UserDto userDto) {
        log.info("[NIK]Creating user " + userDto.getUsername() + "...");
        userService.saveUser(userDto);
    }

    @GetMapping
    public User getUsers(Principal principal) {
        log.info("Current user is " + principal.getName());
        return userService.getUserByUsername("User1");
    }
}
