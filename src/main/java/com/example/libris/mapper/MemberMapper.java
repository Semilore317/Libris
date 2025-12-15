package com.example.libris.mapper;

import com.example.libris.dto.MemberRequestDTO;
import com.example.libris.dto.MemberResponseDTO;
import com.example.libris.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public MemberResponseDTO memberToMemberResponseDTO(Member member) {
        if (member == null) {
            return null;
        }

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setId(member.getId());
        memberResponseDTO.setMembershipNumber(member.getMembershipNumber());
        memberResponseDTO.setJoinedAt(member.getJoinedAt());
        memberResponseDTO.setFirstName(member.getFirstName());
        memberResponseDTO.setMiddleName(member.getMiddleName());
        memberResponseDTO.setLastName(member.getLastName());
        memberResponseDTO.setEmail(member.getEmail());

        return memberResponseDTO;
    }

    public Member memberRequestDTOToMember(MemberRequestDTO memberRequestDTO) {
        if (memberRequestDTO == null) {
            return null;
        }

        Member member = new Member();
        member.setFirstName(memberRequestDTO.getFirstName());
        member.setMiddleName(memberRequestDTO.getMiddleName());
        member.setLastName(memberRequestDTO.getLastName());
        member.setEmail(memberRequestDTO.getEmail());

        return member;
    }

    public void updateMemberFromDto(MemberRequestDTO memberRequestDTO, Member member) {
        if (memberRequestDTO.getFirstName() != null) {
            member.setFirstName(memberRequestDTO.getFirstName());
        }
        if (memberRequestDTO.getMiddleName() != null) {
            member.setMiddleName(memberRequestDTO.getMiddleName());
        }
        if (memberRequestDTO.getLastName() != null) {
            member.setLastName(memberRequestDTO.getLastName());
        }
        if (memberRequestDTO.getEmail() != null) {
            member.setEmail(memberRequestDTO.getEmail());
        }
    }
}
