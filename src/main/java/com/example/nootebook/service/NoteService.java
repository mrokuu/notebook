package com.example.nootebook.service;

import com.example.nootebook.domain.User;
import com.example.nootebook.repository.NoteRepository;
import com.example.nootebook.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public void deleteAccount(User user) {
        noteRepository.deleteByUserId(user.getId());
        userRepository.deleteById(user.getId());
    }
}
