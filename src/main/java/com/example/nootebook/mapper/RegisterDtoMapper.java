package com.example.nootebook.mapper;


import com.example.nootebook.domain.User;
import com.example.nootebook.dto.RegisterDTO;
import org.springframework.stereotype.Service;

@Service
public class RegisterDtoMapper {

    public static User mapToUser(RegisterDTO registerDTO, String token) {
        return User.builder()
                .username(registerDTO.getUsername())
                .password(registerDTO.getPassword())
                .email(registerDTO.getEmail())
                .activationToken(token)
                .build();
    }


}
