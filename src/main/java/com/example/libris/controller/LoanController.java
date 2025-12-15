package com.example.libris.controller;

import com.example.libris.dto.LoanRequestDTO;
import com.example.libris.entity.Loan;
import com.example.libris.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/checkout")
    public ResponseEntity<Loan> checkoutBook(@Valid @RequestBody LoanRequestDTO loanRequestDTO) {
        Loan loan = loanService.checkoutBook(loanRequestDTO.getBookInstanceId(), loanRequestDTO.getMemberId(), loanRequestDTO.getDueDate());
        return ResponseEntity.ok(loan);
    }
}
