package com.example.libris.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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
