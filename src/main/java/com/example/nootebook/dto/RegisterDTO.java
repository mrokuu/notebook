package com.example.nootebook.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDTO {

    @Size(min = 5, max = 30, message = "Nazwa użytkownika powinna zawierać co najmniej 5 znaków.")
    private String username;
    @Size(min = 5, message = "Hasło powinno zawierać co najmniej 5 znaków.")
    private String password;
    private String passwordRepeat;
    @Email(message = "Wprowadz poprawny adres E-Mail.")
    @NotEmpty(message = "Email nie moze byc pusty.")
    private String email;
}
