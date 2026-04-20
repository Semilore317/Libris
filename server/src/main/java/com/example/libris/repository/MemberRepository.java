package com.example.libris.repository;

import com.example.libris.entity.Member;
import com.example.libris.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMembershipNumber(String membershipNumber);

    Optional<Member> findByUser(User user);
}