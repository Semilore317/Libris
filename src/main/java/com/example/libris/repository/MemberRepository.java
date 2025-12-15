package com.example.libris.repository;

import com.example.libris.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    java.util.Optional<Member> findByMembershipNumber(String membershipNumber);
}