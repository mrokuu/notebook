package com.example.nootebook.controller.auth;

import com.example.nootebook.dto.RegisterDTO;
import com.example.nootebook.service.RegisterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute(new RegisterDTO());
        return "register";
    }


}
