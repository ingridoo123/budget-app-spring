package com.example.budget_app.service;

import com.example.budget_app.dto.BalanceDto;
import com.example.budget_app.model.Balance;
import com.example.budget_app.repository.ExpenseRepository;
import com.example.budget_app.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BalanceService {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;

    public BalanceDto getCurrentBalance() {
        BigDecimal totalIncome = incomeRepository.getTotalIncome();
        BigDecimal totalExpenses = expenseRepository.getTotalExpenses();
        
        totalIncome = totalIncome != null ? totalIncome : BigDecimal.ZERO;
        totalExpenses = totalExpenses != null ? totalExpenses : BigDecimal.ZERO;
        
        Balance balance = new Balance().calculateBalance(totalIncome, totalExpenses);
        
        return new BalanceDto(
                balance.getTotalIncome(),
                balance.getTotalExpenses(),
                balance.getCurrentBalance(),
                balance.getCalculatedDate()
        );
    }

    public BalanceDto getBalanceByDateRange(LocalDate startDate, LocalDate endDate) {
        BigDecimal totalIncome = incomeRepository.getTotalIncomeByDateRange(startDate, endDate);
        BigDecimal totalExpenses = expenseRepository.getTotalExpensesByDateRange(startDate, endDate);
        
        totalIncome = totalIncome != null ? totalIncome : BigDecimal.ZERO;
        totalExpenses = totalExpenses != null ? totalExpenses : BigDecimal.ZERO;
        
        Balance balance = new Balance().calculateBalance(totalIncome, totalExpenses);
        
        return new BalanceDto(
                balance.getTotalIncome(),
                balance.getTotalExpenses(),
                balance.getCurrentBalance(),
                LocalDate.now()
        );
    }
}
