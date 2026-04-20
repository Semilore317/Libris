package com.example.libris.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberRequestDTO {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Email(message = "Provide a valid Email address")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
