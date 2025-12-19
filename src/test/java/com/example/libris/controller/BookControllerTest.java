package com.example.libris.controller;

import com.example.libris.dto.BookRequestDTO;
import com.example.libris.dto.BookResponseDTO;
import com.example.libris.enums.BookGenre;
import com.example.libris.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Year;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private com.example.libris.config.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private com.example.libris.services.impl.CustomUserDetailsService customUserDetailsService;

    @MockBean
    private com.example.libris.repository.UserRepository userRepository;

    @MockBean
    private com.example.libris.repository.MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addBook_shouldReturnCreatedBook() throws Exception {
        BookRequestDTO requestDTO = new BookRequestDTO();
        requestDTO.setTitle("The Lord of the Rings");
        requestDTO.setAuthor("J.R.R. Tolkien");
        requestDTO.setISBN("978-0-618-64015-7");
        requestDTO.setGenre(BookGenre.FANTASY);
        requestDTO.setPublicationYear(Year.of(1954));

        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitle("The Lord of the Rings");
        responseDTO.setAuthor("J.R.R. Tolkien");
        responseDTO.setISBN("978-0-618-64015-7");
        responseDTO.setGenre(BookGenre.FANTASY);
        responseDTO.setPublicationYear(Year.of(1954));

        given(bookService.createBook(any(BookRequestDTO.class))).willReturn(responseDTO);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.author").value("J.R.R. Tolkien"))
                .andExpect(jsonPath("$.ISBN").value("978-0-618-64015-7"));
    }

    @Test
    public void getBook_shouldReturnBook() throws Exception {
        BookResponseDTO responseDTO = new BookResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setTitle("The Lord of the Rings");
        responseDTO.setAuthor("J.R.R. Tolkien");
        responseDTO.setISBN("978-0-618-64015-7");
        responseDTO.setGenre(BookGenre.FANTASY);
        responseDTO.setPublicationYear(Year.of(1954));

        given(bookService.findBookByIdWithAvailability(1L)).willReturn(responseDTO);

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("The Lord of the Rings"))
                .andExpect(jsonPath("$.author").value("J.R.R. Tolkien"))
                .andExpect(jsonPath("$.ISBN").value("978-0-618-64015-7"));
    }
}
