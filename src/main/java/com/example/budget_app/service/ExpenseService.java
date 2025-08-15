package com.example.budget_app.service;


import com.example.budget_app.dto.ExpenseDto;
import com.example.budget_app.excepiton.ResourceNotFoundException;
import com.example.budget_app.model.Expense;
import com.example.budget_app.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public List<ExpenseDto> findAll() {
        return expenseRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public ExpenseDto save(ExpenseDto expenseDto) {
        Expense expense = mapToEntity(expenseDto);
        Expense savedExpense = expenseRepository.save(expense);
        return mapToDto(savedExpense);
    }

    public ExpenseDto update(Long id, ExpenseDto expenseDto) {
        Expense existingExpense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        existingExpense.setDescription(expenseDto.description());
        existingExpense.setAmount(expenseDto.amount());
        existingExpense.setDate(expenseDto.date());

        Expense updatedExpense = expenseRepository.save(existingExpense);

        return mapToDto(updatedExpense);
    }

    public void delete(Long id) {
        if(!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense not found with id: " + id);
        } else {
            expenseRepository.deleteById(id);
        }
    }

    private ExpenseDto mapToDto(Expense expense) {
        return new ExpenseDto(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getDate()
        );
    }

    private Expense mapToEntity(ExpenseDto expenseDto) {
        Expense expense = new Expense();
        expense.setDescription(expenseDto.description());
        expense.setAmount(expenseDto.amount());
        expense.setDate(expenseDto.date());
        return expense;
    }


}
