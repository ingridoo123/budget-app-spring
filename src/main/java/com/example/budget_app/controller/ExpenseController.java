package com.example.budget_app.controller;


import com.example.budget_app.dto.ExpenseDto;
import com.example.budget_app.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto) {
        ExpenseDto savedExpense = expenseService.save(expenseDto);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id, @RequestBody ExpenseDto expenseDto) {
        ExpenseDto updatedExpense = expenseService.update(id,expenseDto);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExpense(@PathVariable Long id) {
        expenseService.delete(id);
    }

}
