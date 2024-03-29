package com.example.nootebook.service;

import com.example.nootebook.config.PasswordEncoder;
import com.example.nootebook.domain.User;
import com.example.nootebook.dto.RegisterDTO;
import com.example.nootebook.exception.EmailAlreadyExistsException;
import com.example.nootebook.exception.PasswordDoNotMatch;
import com.example.nootebook.exception.UsernameAlreadyExistsException;
import com.example.nootebook.mapper.RegisterDtoMapper;
import com.example.nootebook.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    public void registerNewUser(RegisterDTO registerDTO) throws PasswordDoNotMatch, UsernameAlreadyExistsException, EmailAlreadyExistsException, MessagingException, UnsupportedEncodingException {
        if (checkPasswordRepeatCorrect(registerDTO)) {
            if (checkUserExsists(registerDTO.getUsername(), registerDTO.getEmail())) {
                String token = generateUUID();
//                emailService.sendEmail(registerDTO.getEmail(), token, "REGISTERUSER");
                User user = RegisterDtoMapper.mapToUser(registerDTO, token);
                user.setActivationTokenExpiredDate(LocalDateTime.now().plusMinutes(15));
                encodePasswordAndSaveToDB(user);
            }
        } else {
            throw new PasswordDoNotMatch("Hasła nie są takie same");
        }
    }


    private boolean checkUserExsists(String username, String email) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        if (!userRepository.existsByEmail(email)) {
            if (!userRepository.existsByUsername(username)) {
                return true;
            }
            throw new UsernameAlreadyExistsException("Nazwa użytkownika " + username + " już istnieje.");
        }
        throw new EmailAlreadyExistsException("Email " + email + " już istnieje");
    }

    private boolean checkPasswordRepeatCorrect(RegisterDTO registerDTO) {
        return registerDTO.getPassword().equals(registerDTO.getPasswordRepeat());
    }

    private void encodePasswordAndSaveToDB(User user) {
        String encodedPassword = passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setCreatedDate(LocalDateTime.now());
        userRepository.save(user);
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
