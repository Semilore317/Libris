package com.example.libris.repository;

import com.example.libris.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByMemberIdAndReturnedAtIsNull(Long memberId);

    Optional<Loan> findByBookInstanceIdAndReturnedAtIsNull(Long bookInstanceId);

    List<Loan> findByDueDateBeforeAndReturnedAtIsNull(LocalDateTime now);
}
