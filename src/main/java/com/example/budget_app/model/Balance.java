package com.example.budget_app.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Balance {
    
    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal currentBalance;
    private LocalDate calculatedDate;
    
    public Balance calculateBalance(BigDecimal income, BigDecimal expenses) {
        this.totalIncome = income != null ? income : BigDecimal.ZERO;
        this.totalExpenses = expenses != null ? expenses : BigDecimal.ZERO;
        this.currentBalance = this.totalIncome.subtract(this.totalExpenses);
        this.calculatedDate = LocalDate.now();
        return this;
    }
}
