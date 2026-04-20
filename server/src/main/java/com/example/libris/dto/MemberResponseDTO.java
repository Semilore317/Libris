package com.example.libris.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberResponseDTO {
    private Long id;
    private String membershipNumber;
    private LocalDate joinedAt;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
}
