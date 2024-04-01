package com.example.nootebook.controller.wnotes;

import com.example.nootebook.domain.User;
import com.example.nootebook.dto.NoteDeleteDTO;
import com.example.nootebook.exception.BadActionException;
import com.example.nootebook.exception.NoteBadOwnerException;
import com.example.nootebook.exception.NoteNotFoundException;
import com.example.nootebook.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class NoteDeleteController {

    private final NoteService noteService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wnotes/delete/{uuid}")
    public String getNoteToDelete(@PathVariable String uuid, @AuthenticationPrincipal User user, Model model) {
        try {
            NoteDeleteDTO noteToDelete = (NoteDeleteDTO) noteService.checkNoteAndMapToRightDto(uuid, user, "DELETE");
            model.addAttribute("note", noteToDelete);
        } catch (NoteNotFoundException | BadActionException | NoteBadOwnerException exception) {
            model.addAttribute("error", exception.getMessage());
        }
        return "note-delete-page";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/wnotes/delete")
    public String deleteNote(@ModelAttribute NoteDeleteDTO note, Model model) {
        noteService.deleteNote(note.getId());
        model.addAttribute("deleteSuccess", true);
        return "menu";
    }
}


