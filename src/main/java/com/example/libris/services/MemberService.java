package com.example.libris.services;

import com.example.libris.dto.MemberRequestDTO;
import com.example.libris.dto.MemberResponseDTO;

import java.util.List;

public interface MemberService {
    MemberResponseDTO addMember(MemberRequestDTO memberRequestDTO);
    List<MemberResponseDTO> getAllMembers();
    MemberResponseDTO getMemberById(Long id);
    MemberResponseDTO updateMember(Long id, MemberRequestDTO memberRequestDTO);
    void deleteMember(Long id);
}