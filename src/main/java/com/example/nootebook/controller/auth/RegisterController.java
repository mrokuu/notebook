package com.example.nootebook.controller.auth;

import com.example.nootebook.dto.RegisterDTO;
import com.example.nootebook.exception.EmailAlreadyExistsException;
import com.example.nootebook.exception.PasswordDoNotMatch;
import com.example.nootebook.exception.UsernameAlreadyExistsException;
import com.example.nootebook.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import javax.validation.Valid;


@Controller
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute(new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String processRegisterUser(@ModelAttribute @Valid RegisterDTO registerDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            try {
                registerService.registerNewUser(registerDTO);
                model.addAttribute("registerSuccess", true);
            } catch (PasswordDoNotMatch | EmailAlreadyExistsException | UsernameAlreadyExistsException exception) {
                model.addAttribute("error", exception.getMessage());
            } catch (MessagingException | UnsupportedEncodingException exception) {
                model.addAttribute("error", "Błąd, nie udało się wysłać E-Maila, skontaktuj się z administratorem strony.");
            }
        }
        return "register";
    }
}
