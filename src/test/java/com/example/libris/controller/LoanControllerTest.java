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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

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

        mockMvc.perform(post("/api/v1/loans/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }
}
