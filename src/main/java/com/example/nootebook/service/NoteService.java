package com.example.nootebook.service;

import com.example.nootebook.domain.User;
import com.example.nootebook.dto.NoteListDTO;
import com.example.nootebook.mapper.NoteDtoMapper;
import com.example.nootebook.repository.NoteRepository;
import com.example.nootebook.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<NoteListDTO> findAllNotesByUser(User user) {
        return noteRepository.findAllByUserId(user.getId())
                .stream()
                .map(NoteDtoMapper::mapNoteToNoteListDto)
                .collect(Collectors.toList());
    }
}
