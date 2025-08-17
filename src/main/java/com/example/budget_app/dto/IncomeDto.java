package com.example.budget_app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeDto(
        Long id,
        String description,
        BigDecimal amount,
        LocalDate date,
        Long categoryId,
        String categoryName
) {
}
