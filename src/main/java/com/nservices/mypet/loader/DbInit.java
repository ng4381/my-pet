package com.nservices.mypet.loader;

import com.nservices.mypet.entity.User;
import com.nservices.mypet.repository.UserRepository;
import com.nservices.mypet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.nservices.mypet.security.RoleConstants.ROLE_ADMIN;
import static com.nservices.mypet.security.RoleConstants.ROLE_USER;

@Service
@RequiredArgsConstructor
@Slf4j
public class DbInit implements CommandLineRunner {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {


        /*
        if (userService.getUserByUsername("User1") != null){
            return;
        }

         */

        //log.info("Creating new user(User1)...");
        //User user1 = new User("User1", passwordEncoder.encode( "1"), 1, ROLE_ADMIN);
        //userService.saveUser(user1);
    }
}