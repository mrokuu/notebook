package com.example.nootebook.controller.wnotes;

import com.example.nootebook.config.UrlProperties;
import com.example.nootebook.domain.User;
import com.example.nootebook.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class NoteListController {

    private final NoteService noteService;
    private final UrlProperties urlProperties;

    //Dodaj wszedzie preauthorize

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wnotes/list/note")
    public String getHistoryNotePage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("noteList", noteService.findAllNotesByUser(user));
        model.addAttribute("adress", urlProperties.getApplicationAdress());
        return "note-list";
    }
}
