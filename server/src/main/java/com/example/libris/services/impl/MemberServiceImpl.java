package com.example.libris.services.impl;

import com.example.libris.dto.MemberRequestDTO;
import com.example.libris.dto.MemberResponseDTO;
import com.example.libris.entity.Member;
import com.example.libris.exception.ResourceNotFoundException;
import com.example.libris.mapper.MemberMapper;
import com.example.libris.repository.MemberRepository;
import com.example.libris.repository.RoleRepository;
import com.example.libris.repository.UserRepository;
import com.example.libris.services.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public MemberResponseDTO addMember(MemberRequestDTO memberRequestDTO) {
        // Create User first
        com.example.libris.entity.User user = new com.example.libris.entity.User();
        user.setUsername(memberRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(memberRequestDTO.getPassword()));

        com.example.libris.entity.Role memberRole = roleRepository.findByName("ROLE_MEMBER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: ROLE_MEMBER"));
        java.util.Set<com.example.libris.entity.Role> roles = new java.util.HashSet<>();
        roles.add(memberRole);
        user.setRoles(roles);

        com.example.libris.entity.User savedUser = userRepository.save(user);

        // Create Member
        Member member = memberMapper.memberRequestDTOToMember(memberRequestDTO);
        member.setUser(savedUser);
        member.setJoinedAt(LocalDate.now());
        member.setMembershipNumber(UUID.randomUUID().toString()); // Generate a unique membership number
        Member savedMember = memberRepository.save(member);
        return memberMapper.memberToMemberResponseDTO(savedMember);
    }

    @Override
    public List<MemberResponseDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(memberMapper::memberToMemberResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MemberResponseDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return memberMapper.memberToMemberResponseDTO(member);
    }

    @Override
    public MemberResponseDTO updateMember(Long id, MemberRequestDTO memberRequestDTO) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        memberMapper.updateMemberFromDto(memberRequestDTO, member);
        Member updatedMember = memberRepository.save(member);
        return memberMapper.memberToMemberResponseDTO(updatedMember);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        memberRepository.delete(member);
    }

    public MemberResponseDTO getMemberByMembershipNumber(String membershipNumber) {
        Member member = memberRepository.findByMembershipNumber(membershipNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with membership number: " + membershipNumber));
        return memberMapper.memberToMemberResponseDTO(member);
    }
}
