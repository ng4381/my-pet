package com.nservices.mypet.controller;

import com.nservices.mypet.dto.UserDto;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("[NIK]Creating user " + userDto.getUsername() + "...");
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @GetMapping
    public ResponseEntity<HttpStatus> getUser(Principal principal) {
        log.info("Credentials: " + principal.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
