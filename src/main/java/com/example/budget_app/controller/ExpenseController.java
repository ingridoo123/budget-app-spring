package com.example.budget_app.controller;

import com.example.budget_app.dto.ExpenseDto;
import com.example.budget_app.service.ExpenseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<ExpenseDto> getAllExpenses() {
        return expenseService.findAll();
    }

    @GetMapping("/date-range")
    public List<ExpenseDto> getExpensesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return expenseService.findByDateRange(startDate, endDate);
    }

    @GetMapping("/category/{categoryId}")
    public List<ExpenseDto> getExpensesByCategory(@PathVariable Long categoryId) {
        return expenseService.findByCategory(categoryId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable Long id) {
        ExpenseDto expense = expenseService.findById(id);
        return ResponseEntity.ok(expense);
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalExpenses() {
        BigDecimal total = expenseService.getTotalExpenses();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/total/date-range")
    public ResponseEntity<BigDecimal> getTotalExpensesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BigDecimal total = expenseService.getTotalExpensesByDateRange(startDate, endDate);
        return ResponseEntity.ok(total);
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto) {
        ExpenseDto savedExpense = expenseService.save(expenseDto);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id, @RequestBody ExpenseDto expenseDto) {
        ExpenseDto updatedExpense = expenseService.update(id, expenseDto);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long id) {
        expenseService.delete(id);
    }
}
