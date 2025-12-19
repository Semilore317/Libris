package com.example.libris.controller;

import com.example.libris.dto.LibraryDashBoardResponseDTO;
import com.example.libris.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/dashboard")
    public ResponseEntity<LibraryDashBoardResponseDTO> getDashboardSummary() {
        return ResponseEntity.ok(reportService.getLibraryDashboardSummary());
    }
}
