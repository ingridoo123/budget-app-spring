package com.example.budget_app.controller;

import com.example.budget_app.dto.BalanceDto;
import com.example.budget_app.service.BalanceService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping
    public ResponseEntity<BalanceDto> getCurrentBalance() {
        BalanceDto balance = balanceService.getCurrentBalance();
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/date-range")
    public ResponseEntity<BalanceDto> getBalanceByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BalanceDto balance = balanceService.getBalanceByDateRange(startDate, endDate);
        return ResponseEntity.ok(balance);
    }
}
