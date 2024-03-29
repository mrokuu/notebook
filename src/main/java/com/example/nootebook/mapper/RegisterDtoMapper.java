package com.example.nootebook.mapper;


import com.example.nootebook.domain.User;
import com.example.nootebook.dto.ChangePasswordDTO;
import com.example.nootebook.dto.RegisterDTO;
import com.example.nootebook.dto.ResetPasswordDTO;
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

    public static ResetPasswordDTO mapToResetPasswordDTO(User user) {
        return ResetPasswordDTO.builder()
                .id(user.getId())
                .resetPasswordToken(user.getResetPasswordToken())
                .build();
    }

    public static ChangePasswordDTO mapToChangePasswordDTO(User user) {
        return ChangePasswordDTO.builder()
                .id(user.getId())
                .build();
    }


}
