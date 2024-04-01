package com.example.nootebook.controller.wnotes;

import com.example.nootebook.config.UrlProperties;
import com.example.nootebook.domain.User;
import com.example.nootebook.dto.NotePrivateDTO;
import com.example.nootebook.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class NotePrivateController {

    private final NoteService noteService;
    private final UrlProperties urlProperties;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wnotes/private/note")
    public String getCreateNewPrivateNotePage(Model model) {
        model.addAttribute(new NotePrivateDTO());
        model.addAttribute("saveNoteSuccess", false);
        return "note-private";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/wnotes/private/note")
    public String createNewPrivateNote(@ModelAttribute @Valid NotePrivateDTO notePrivateDTO, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("saveNoteSuccess", false);
        } else {
            noteService.saveNewPrivateNote(notePrivateDTO, user);
            model.addAttribute("note", notePrivateDTO);
            model.addAttribute("adress", urlProperties.getApplicationAdress());
            model.addAttribute("saveSuccesfull", true);
        }
        return "menu";
    }
}

