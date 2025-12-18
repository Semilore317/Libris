package com.example.libris.services;

import com.example.libris.entity.Loan;
//import com.example.libris.dto.LoanRequestDTO; // Assume a simple DTO exists for input

import java.util.List;

public interface LoanService {

    // 1. Checkout Method (Must return the created Loan object)
    Loan checkoutBook(Long bookInstanceId, String username, java.time.LocalDate dueDate);

    // 2. Return Method (Must handle the date of return and update statuses)
    Loan returnBook(Long loanId);

    // 3. Find Active Loans for a Member (Crucial for due date tracking)
    List<Loan> findActiveLoansByMember(Long memberId);

    // 4. Find Active Loans for an Instance (Prevent double-checkout)
    Loan findActiveLoanByInstance(Long bookInstanceId);

    List<Loan> findMyLoans(String username);

    // 5. Find Overdue Loans (For your "nice to have" fee calculation)
    List<Loan> findOverdueLoans();
    // Method to add physical copies to an existing book title
}