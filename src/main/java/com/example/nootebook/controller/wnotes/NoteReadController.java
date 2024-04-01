package com.example.nootebook.controller.wnotes;

import com.example.nootebook.domain.User;
import com.example.nootebook.dto.NoteDTO;
import com.example.nootebook.exception.NoteBadOwnerException;
import com.example.nootebook.exception.NoteExpiredException;
import com.example.nootebook.exception.NoteNotFoundException;
import com.example.nootebook.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class NoteReadController {

    private final NoteService noteService;


    @GetMapping("/note/{uuid}")
    public String getNotePage(@PathVariable String uuid, @AuthenticationPrincipal User user, Model model) {
        try {
            NoteDTO noteByUUID = noteService.findNote(uuid, user);
            model.addAttribute("noteByUUID", noteByUUID);
        } catch (NoteNotFoundException | NoteExpiredException | NoteBadOwnerException exception) {
            model.addAttribute("error", exception.getMessage());
        }
        return "view-note";
    }

}
