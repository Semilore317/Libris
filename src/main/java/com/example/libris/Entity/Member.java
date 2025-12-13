package com.example.libris.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String membershipNumber;

    @Column(nullable = false)
    private LocalDate joinedAt;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = true) // nullable by default - just tryna be explicit
    private String middleName;

    @Column(nullable = false)
    private String  lastName;

    @Email(message = "Provide a valid Email address")
    @Column(unique = true, nullable = false)
    private String email;

    // history

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Loan> loans;
}
