package com.example.budget_app.service;

import com.example.budget_app.dto.ExpenseDto;
import com.example.budget_app.excepiton.ResourceNotFoundException;
import com.example.budget_app.model.Category;
import com.example.budget_app.model.Expense;
import com.example.budget_app.repository.CategoryRepository;
import com.example.budget_app.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public List<ExpenseDto> findAll() {
        return expenseRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ExpenseDto> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByDateBetween(startDate, endDate).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<ExpenseDto> findByCategory(Long categoryId) {
        return expenseRepository.findByCategoryId(categoryId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ExpenseDto findById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        return mapToDto(expense);
    }

    public ExpenseDto save(ExpenseDto expenseDto) {
        Expense expense = mapToEntity(expenseDto);
        Expense savedExpense = expenseRepository.save(expense);
        return mapToDto(savedExpense);
    }

    public ExpenseDto update(Long id, ExpenseDto expenseDto) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        existingExpense.setDescription(expenseDto.description());
        existingExpense.setAmount(expenseDto.amount());
        existingExpense.setDate(expenseDto.date());

        if (expenseDto.categoryId() != null) {
            Category category = categoryRepository.findById(expenseDto.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + expenseDto.categoryId()));
            existingExpense.setCategory(category);
        }

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return mapToDto(updatedExpense);
    }

    public void delete(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }

    public BigDecimal getTotalExpenses() {
        BigDecimal total = expenseRepository.getTotalExpenses();
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getTotalExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        BigDecimal total = expenseRepository.getTotalExpensesByDateRange(startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    private ExpenseDto mapToDto(Expense expense) {
        return new ExpenseDto(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate(),
                expense.getCategory() != null ? expense.getCategory().getId() : null,
                expense.getCategory() != null ? expense.getCategory().getName() : null
        );
    }

    private Expense mapToEntity(ExpenseDto expenseDto) {
        Expense expense = new Expense();
        expense.setDescription(expenseDto.description());
        expense.setAmount(expenseDto.amount());
        expense.setDate(expenseDto.date());

        if (expenseDto.categoryId() != null) {
            Category category = categoryRepository.findById(expenseDto.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + expenseDto.categoryId()));
            expense.setCategory(category);
        }

        return expense;
    }
}
