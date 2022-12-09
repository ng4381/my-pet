package com.nservices.mypet.service;

import com.nservices.mypet.dto.UserDto;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.exception.UserNotFoundException;
import com.nservices.mypet.exception.UsernameIsNotUniqueException;
import com.nservices.mypet.mapper.UserMapper;
import com.nservices.mypet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User username: " + username + " not found!");
        }
        return userRepository.findByUsername(username);
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new UserNotFoundException("User username: " + username + " not found!");
        }
        return user;
    }

    public UserDto saveUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new UsernameIsNotUniqueException("User (" + userDto.getUsername() + ") already exists!");
        }
        User user = userRepository.save(userMapper.ObjectToEntity(userDto));
        return userDto;
    }


}
