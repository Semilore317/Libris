package com.example.libris.controller;

import com.example.libris.dto.LoanRequestDTO;
import com.example.libris.entity.Loan;
import com.example.libris.entity.Member;
import com.example.libris.entity.User;
import com.example.libris.exception.ResourceNotFoundException;
import com.example.libris.repository.MemberRepository;
import com.example.libris.repository.UserRepository;
import com.example.libris.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/checkout")
    public ResponseEntity<Loan> checkoutBook(@Valid @RequestBody LoanRequestDTO loanRequestDTO, Principal principal) {
        User user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + principal.getName()));

        boolean isLibrarian = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_LIBRARIAN"));

        Long targetMemberId;

        if (isLibrarian) {
            // Librarian must provide memberId
            if (loanRequestDTO.getMemberId() == null) {
                throw new IllegalArgumentException("Librarian must provide memberId");
            }
            targetMemberId = loanRequestDTO.getMemberId();
        } else {
            // Member checkout for self
            Member member = memberRepository.findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Member profile not found for user: " + principal.getName()));
            targetMemberId = member.getId();
        }

        Loan loan = loanService.checkoutBook(loanRequestDTO.getBookInstanceId(), targetMemberId,
                loanRequestDTO.getDueDate());
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/my-history")
    public ResponseEntity<List<Loan>> myHistory(Principal principal) {
        List<Loan> loans = loanService.findMyLoans(principal.getName());
        return ResponseEntity.ok(loans);
    }
}
