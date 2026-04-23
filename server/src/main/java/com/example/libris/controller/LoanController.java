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
            if (loanRequestDTO.getMemberId() == null) {
                throw new IllegalArgumentException("Librarian must provide memberId");
            }
            targetMemberId = loanRequestDTO.getMemberId();
        } else {
            Member member = memberRepository.findByUser(user)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Member profile not found for user: " + principal.getName()));
            targetMemberId = member.getId();
        }

        java.time.LocalDate dueDate = loanRequestDTO.getDueDate() != null 
                ? loanRequestDTO.getDueDate() 
                : java.time.LocalDate.now().plusDays(14);

        Loan loan;
        if (loanRequestDTO.getBookInstanceId() != null) {
            loan = loanService.checkoutBook(loanRequestDTO.getBookInstanceId(), targetMemberId, dueDate);
        } else if (loanRequestDTO.getBookId() != null) {
            loan = loanService.checkoutBookByTitle(loanRequestDTO.getBookId(), targetMemberId, dueDate);
        } else {
            throw new IllegalArgumentException("Either bookInstanceId or bookId must be provided");
        }
        
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/my-history")
    public ResponseEntity<List<Loan>> myHistory(Principal principal) {
        List<Loan> loans = loanService.findMyLoans(principal.getName());
        return ResponseEntity.ok(loans);
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.findAllLoans());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Loan>> getOverdueLoans() {
        return ResponseEntity.ok(loanService.findOverdueLoans());
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnBook(id));
    }
}
