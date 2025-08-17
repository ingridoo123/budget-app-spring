package com.example.budget_app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BalanceDto(
        BigDecimal totalIncome,
        BigDecimal totalExpenses,
        BigDecimal currentBalance,
        LocalDate calculatedDate
) {
}
