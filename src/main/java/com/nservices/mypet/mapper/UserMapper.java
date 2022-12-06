package com.nservices.mypet.mapper;

import com.nservices.mypet.dto.UserDto;
import com.nservices.mypet.entity.User;
import com.nservices.mypet.security.RoleConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IDtoMapper<UserDto, User>{

    private final PasswordEncoder passwordEncoder;

    public UserMapper(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User ObjectToEntity(UserDto userDto) {
        return new User(userDto.getUsername(),
                passwordEncoder.encode( userDto.getPassword()),
                1,
                RoleConstants.ROLE_USER);
    }

    @Override
    public UserDto EntityToObject(User user) {
        return null;
    }
}
