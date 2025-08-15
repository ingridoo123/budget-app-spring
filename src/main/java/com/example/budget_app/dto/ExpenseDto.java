package com.example.budget_app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseDto(
        Long id,
        String description,
        BigDecimal amount,
        LocalDate date
) {
}
