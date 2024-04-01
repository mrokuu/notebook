package com.example.nootebook.service;

import com.example.nootebook.domain.Note;
import com.example.nootebook.domain.User;
import com.example.nootebook.dto.*;
import com.example.nootebook.exception.BadActionException;
import com.example.nootebook.exception.NoteBadOwnerException;
import com.example.nootebook.exception.NoteExpiredException;
import com.example.nootebook.exception.NoteNotFoundException;
import com.example.nootebook.mapper.NoteDtoMapper;
import com.example.nootebook.repository.NoteRepository;
import com.example.nootebook.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
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

    public NoteDTO findNote(String uuid, User user) throws NoteExpiredException, NoteBadOwnerException, NoteNotFoundException {
        Note note = findNoteByUUIDFromDB(uuid);
        if (!note.isPrivNote()) {
            if (note.getExpiredDate() != null) {
                if (LocalDate.now().isBefore(note.getExpiredDate())) {
                    return NoteDtoMapper.mapNoteToNoteDto(note);
                } else {
                    throw new NoteExpiredException("Notatka o numerze " + uuid + " wygasła dnia " + note.getExpiredDate());
                }
            } else {
                return NoteDtoMapper.mapNoteToNoteDto(note);
            }
        }
        if (note.isPrivNote()) {
            if (note.getExpiredDate() != null) {
                if (LocalDate.now().isBefore(note.getExpiredDate())) {
                    if (checkNoteBelongsTheUser(user, note)) {
                        return NoteDtoMapper.mapNoteToNoteDto(note);
                    } else {
                        throw new NoteBadOwnerException("Notatka o numerze " + uuid + " jest prywatna i nie możesz jej zobaczyć");
                    }
                } else {
                    throw new NoteExpiredException("Notatka o numerze " + uuid + " wygasła dnia " + note.getExpiredDate());
                }
            } else {
                if (checkNoteBelongsTheUser(user, note)) {
                    return NoteDtoMapper.mapNoteToNoteDto(note);
                }
                throw new NoteBadOwnerException("Notatka o numerze " + uuid + " jest prywatna i nie możesz jej zobaczyć");
            }
        } else {
            throw new NoteBadOwnerException("Notatka o numerze " + uuid + " jest prywatna i nie możesz jej zobaczyć");
        }
    }

    public Object checkNoteAndMapToRightDto(String uuid, User user, String action) throws NoteNotFoundException, NoteBadOwnerException, BadActionException {
        Note note = findNoteByUUIDFromDB(uuid);
        if (checkNoteBelongsTheUser(user, note)) {
            return switch (action) {
                case "DELETE" -> NoteDtoMapper.mapNoteToNoteDeleteDTO(note);
                case "EDIT" -> NoteDtoMapper.mapNoteToNoteEditDto(note);
                default -> throw new BadActionException("Nieprawidłowe polecenie " + action);
            };
        } else {
            throw new NoteBadOwnerException("Nie jesteś właścicielem notatki");
        }
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public void updateNote(NoteEditDTO noteDTO, User user) throws NoteNotFoundException, NoteBadOwnerException {
        Note note = NoteDtoMapper.mapNoteEditDtoToNote(noteDTO);
        if (checkNoteBelongsTheUser(user, note)) {
            note.setUpdatedDate(LocalDate.now());
            noteRepository.save(note);
        } else {
            throw new NoteBadOwnerException("Nie jesteś właścicielem notatki.");
        }
    }

    public void saveNewPrivateNote(NotePrivateDTO notePrivateDTO, User user) {
        String uuid = generateUUID();
        Note note = NoteDtoMapper.mapNotePrivateDtoToNote(notePrivateDTO);
        notePrivateDTO.setUuid(uuid);
        note.setUuid(uuid);
        note.setPrivNote(true);
        note.setUser(user);
        note.setCreatedDate(LocalDate.now());
        noteRepository.save(note);
    }

    public void saveNewTimeNote(NoteTimeDTO noteTimeDTO, User user) {
        String uuid = generateUUID();
        Note note = NoteDtoMapper.mapNoteTimeDtoToNote(noteTimeDTO);
        note.setUuid(uuid);
        note.setUser(user);
        note.setCreatedDate(LocalDate.now());
        noteRepository.save(note);
    }

    public NoteDTO saveNewNote(NoteDTO noteDTO, User user) {
        String uuid = generateUUID();
        Note note = NoteDtoMapper.mapNoteDtoToNote(noteDTO);
        note.setCreatedDate(LocalDate.now());
        note.setUuid(uuid);
        note.setUser(user);
        noteDTO.setUuid(uuid);
        if (user != null) {
            noteDTO.setUser(user);
        }
        noteRepository.save(note);
        return noteDTO;
    }

    private boolean checkNoteBelongsTheUser(User user, Note note) {
        return note.getUser().equals(user);
    }

    private Note findNoteByUUIDFromDB(String uuid) throws NoteNotFoundException {
        Note note = noteRepository.findByUuid(uuid);
        if (note != null) {
            return note;
        } else {
            throw new NoteNotFoundException("Notatka o numerze " + uuid + " nie została odnaleziona");
        }
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
