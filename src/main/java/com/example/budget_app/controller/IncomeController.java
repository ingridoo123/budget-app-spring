package com.example.budget_app.controller;

import com.example.budget_app.dto.IncomeDto;
import com.example.budget_app.service.IncomeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public List<IncomeDto> getAllIncomes() {
        return incomeService.findAll();
    }

    @GetMapping("/date-range")
    public List<IncomeDto> getIncomesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return incomeService.findByDateRange(startDate, endDate);
    }

    @GetMapping("/category/{categoryId}")
    public List<IncomeDto> getIncomesByCategory(@PathVariable Long categoryId) {
        return incomeService.findByCategory(categoryId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeDto> getIncomeById(@PathVariable Long id) {
        IncomeDto income = incomeService.findById(id);
        return ResponseEntity.ok(income);
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> getTotalIncome() {
        BigDecimal total = incomeService.getTotalIncome();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/total/date-range")
    public ResponseEntity<BigDecimal> getTotalIncomeByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        BigDecimal total = incomeService.getTotalIncomeByDateRange(startDate, endDate);
        return ResponseEntity.ok(total);
    }

    @PostMapping
    public ResponseEntity<IncomeDto> createIncome(@RequestBody IncomeDto incomeDto) {
        IncomeDto savedIncome = incomeService.save(incomeDto);
        return new ResponseEntity<>(savedIncome, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeDto> updateIncome(@PathVariable Long id, @RequestBody IncomeDto incomeDto) {
        IncomeDto updatedIncome = incomeService.update(id, incomeDto);
        return ResponseEntity.ok(updatedIncome);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIncome(@PathVariable Long id) {
        incomeService.delete(id);
    }
}
