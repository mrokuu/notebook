package com.example.nootebook.controller.account;

import com.example.nootebook.domain.User;
import com.example.nootebook.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class DeleteAccountController {

    private final NoteService noteService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/wnotes/account/delete")
    public String getDeleteAccountPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("notes", noteService.findAllNotesByUser(user));
        return "account-delete";
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/wnotes/account/delete")
    public String deleteAccount(@AuthenticationPrincipal User user) {
        noteService.deleteAccount(user);
        return "redirect:/logout";
    }
}
