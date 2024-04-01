package com.example.nootebook.mapper;

import com.example.nootebook.domain.Note;
import com.example.nootebook.dto.NoteListDTO;
import org.springframework.stereotype.Service;

@Service
public class NoteDtoMapper {


    public static NoteListDTO mapNoteToNoteListDto(Note note) {
        return NoteListDTO.builder()
                .uuid(note.getUuid())
                .title(note.getTitle())
                .privateNote(note.isPrivNote())
                .updatedDate(note.getUpdatedDate())
                .createdDate(note.getCreatedDate())
                .expiredDate(note.getExpiredDate())
                .build();
    }
}
