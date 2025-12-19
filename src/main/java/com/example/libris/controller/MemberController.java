package com.example.libris.controller;

import com.example.libris.dto.MemberRequestDTO;
import com.example.libris.dto.MemberResponseDTO;
import com.example.libris.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDTO> addMember(@Valid @RequestBody MemberRequestDTO memberRequestDTO) {
        MemberResponseDTO newMember = memberService.addMember(memberRequestDTO);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
        List<MemberResponseDTO> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) {
        MemberResponseDTO member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberRequestDTO memberRequestDTO) {
        MemberResponseDTO updatedMember = memberService.updateMember(id, memberRequestDTO);
        return ResponseEntity.ok(updatedMember);
    }

    @GetMapping("/lookup")
    public ResponseEntity<MemberResponseDTO> getMemberByMembershipNumber(@RequestParam String membershipNumber) {
        MemberResponseDTO member = memberService.getMemberByMembershipNumber(membershipNumber);
        return ResponseEntity.ok(member);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
