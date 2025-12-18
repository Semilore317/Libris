package com.example.libris.controller;

import com.example.libris.dto.LoanRequestDTO;
import com.example.libris.entity.Loan;
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

    @PostMapping("/checkout")
    public ResponseEntity<Loan> checkoutBook(@Valid @RequestBody LoanRequestDTO loanRequestDTO, Principal principal) {
        Loan loan = loanService.checkoutBook(loanRequestDTO.getBookInstanceId(), principal.getName(), loanRequestDTO.getDueDate());
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/my-history")
    public ResponseEntity<List<Loan>> myHistory(Principal principal) {
        List<Loan> loans = loanService.findMyLoans(principal.getName());
        return ResponseEntity.ok(loans);
    }
}
