package com.example.libris.services.impl;

import com.example.libris.entity.BookInstance;
import com.example.libris.entity.Loan;
import com.example.libris.entity.Member;
import com.example.libris.enums.BookEnum;
import com.example.libris.exception.BookUnavailableException;
import com.example.libris.exception.LoanLimitExceededException;
import com.example.libris.exception.ResourceNotFoundException;
import com.example.libris.repository.BookInstanceRepository;
import com.example.libris.repository.LoanRepository;
import com.example.libris.repository.MemberRepository;
import com.example.libris.services.LoanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class LoanServiceImpl implements LoanService {

    private static final int MAX_ACTIVE_LOANS = 5;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Loan checkoutBook(Long bookInstanceId, Long memberId, java.time.LocalDate dueDate) {
        BookInstance bookInstance = bookInstanceRepository.findById(bookInstanceId)
                .orElseThrow(() -> new ResourceNotFoundException("BookInstance not found with id: " + bookInstanceId));

        if (bookInstance.getStatus() != BookEnum.AVAILABLE) {
            throw new BookUnavailableException("Book is not available for loan");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        if (findActiveLoansByMember(memberId).size() >= MAX_ACTIVE_LOANS) {
            throw new LoanLimitExceededException("Member has reached the maximum loan limit of " + MAX_ACTIVE_LOANS);
        }

        Loan loan = Loan.builder()
                .bookInstance(bookInstance)
                .member(member)
                .borrowedAt(LocalDateTime.now())
                .dueDate(dueDate.atStartOfDay())
                .build();

        bookInstance.setStatus(BookEnum.ON_LOAN);
        bookInstanceRepository.save(bookInstance);

        return loanRepository.save(loan);
    }

    @Override
    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + loanId));

        loan.setReturnedAt(LocalDateTime.now());
        BookInstance bookInstance = loan.getBookInstance();
        bookInstance.setStatus(BookEnum.AVAILABLE);
        bookInstanceRepository.save(bookInstance);

        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> findActiveLoansByMember(Long memberId) {
        return loanRepository.findByMemberIdAndReturnedAtIsNull(memberId);
    }

    @Override
    public Loan findActiveLoanByInstance(Long bookInstanceId) {
        Optional<Loan> loan = loanRepository.findByBookInstanceIdAndReturnedAtIsNull(bookInstanceId);
        return loan.orElse(null);
    }

    @Override
    public List<Loan> findOverdueLoans() {
        return loanRepository.findByDueDateBeforeAndReturnedAtIsNull(LocalDateTime.now());
    }
}