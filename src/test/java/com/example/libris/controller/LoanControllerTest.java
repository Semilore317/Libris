package com.example.libris.controller;

import com.example.libris.dto.LoanRequestDTO;
import com.example.libris.entity.Loan;
import com.example.libris.services.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @MockBean
    private com.example.libris.repository.UserRepository userRepository;

    @MockBean
    private com.example.libris.repository.MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void checkoutBook_shouldReturnCreatedLoan() throws Exception {
        LoanRequestDTO requestDTO = new LoanRequestDTO();
        requestDTO.setBookInstanceId(1L);
        requestDTO.setMemberId(1L);
        requestDTO.setDueDate(LocalDate.now().plusWeeks(2));

        Loan loan = new Loan();
        loan.setId(1L);
        loan.setBorrowedAt(LocalDateTime.now());
        loan.setDueDate(requestDTO.getDueDate().atStartOfDay());

        given(loanService.checkoutBook(anyLong(), anyLong(), any(LocalDate.class))).willReturn(loan);
        Principal principal = () -> "user";
        com.example.libris.entity.User user = new com.example.libris.entity.User();
        user.setId(1L);
        user.setUsername("user");
        com.example.libris.entity.Member member = new com.example.libris.entity.Member();
        member.setId(1L);
        member.setUser(user);
        given(userRepository.findByUsername("user")).willReturn(java.util.Optional.of(user));
        given(memberRepository.findByUser(user)).willReturn(java.util.Optional.of(member));

        mockMvc.perform(post("/api/v1/loans/checkout")
                        .principal(principal)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }
}
