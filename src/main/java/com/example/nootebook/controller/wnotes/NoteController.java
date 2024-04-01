package com.example.nootebook.controller.wnotes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoteController {

    @GetMapping("/wnotes/menu")
    public String getMenuPage() {
        return "menu";
    }

}
