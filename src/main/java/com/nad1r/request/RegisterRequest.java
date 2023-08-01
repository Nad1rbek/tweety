package com.nad1r.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegisterRequest {
    private String name;

    @NotBlank(message = "Username mustn't be blank")
    private String username;

    @NotBlank(message = "Email mustn't be blank")
    @Email
    private String email;

    @NotBlank(message = "Password mustn't be blank")
    @Size(min = 8, max = 16)
    @Pattern(message = "Password must have at least 1 uppercase 1 lowercase and 1 number",
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;

    private LocalDate birthday;
}
