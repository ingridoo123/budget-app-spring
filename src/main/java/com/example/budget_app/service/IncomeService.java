package com.example.budget_app.service;

import com.example.budget_app.dto.IncomeDto;
import com.example.budget_app.excepiton.ResourceNotFoundException;
import com.example.budget_app.model.Category;
import com.example.budget_app.model.Income;
import com.example.budget_app.repository.CategoryRepository;
import com.example.budget_app.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;

    public List<IncomeDto> findAll() {
        return incomeRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<IncomeDto> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return incomeRepository.findByDateBetween(startDate, endDate).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<IncomeDto> findByCategory(Long categoryId) {
        return incomeRepository.findByCategoryId(categoryId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public IncomeDto findById(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found with id: " + id));
        return mapToDto(income);
    }

    public IncomeDto save(IncomeDto incomeDto) {
        Income income = mapToEntity(incomeDto);
        Income savedIncome = incomeRepository.save(income);
        return mapToDto(savedIncome);
    }

    public IncomeDto update(Long id, IncomeDto incomeDto) {
        Income existingIncome = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found with id: " + id));

        existingIncome.setDescription(incomeDto.description());
        existingIncome.setAmount(incomeDto.amount());
        existingIncome.setDate(incomeDto.date());

        if (incomeDto.categoryId() != null) {
            Category category = categoryRepository.findById(incomeDto.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + incomeDto.categoryId()));
            existingIncome.setCategory(category);
        }

        Income updatedIncome = incomeRepository.save(existingIncome);
        return mapToDto(updatedIncome);
    }

    public void delete(Long id) {
        if (!incomeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Income not found with id: " + id);
        }
        incomeRepository.deleteById(id);
    }

    public BigDecimal getTotalIncome() {
        BigDecimal total = incomeRepository.getTotalIncome();
        return total != null ? total : BigDecimal.ZERO;
    }

    public BigDecimal getTotalIncomeByDateRange(LocalDate startDate, LocalDate endDate) {
        BigDecimal total = incomeRepository.getTotalIncomeByDateRange(startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    private IncomeDto mapToDto(Income income) {
        return new IncomeDto(
                income.getId(),
                income.getDescription(),
                income.getAmount(),
                income.getDate(),
                income.getCategory() != null ? income.getCategory().getId() : null,
                income.getCategory() != null ? income.getCategory().getName() : null
        );
    }

    private Income mapToEntity(IncomeDto incomeDto) {
        Income income = new Income();
        income.setDescription(incomeDto.description());
        income.setAmount(incomeDto.amount());
        income.setDate(incomeDto.date());

        if (incomeDto.categoryId() != null) {
            Category category = categoryRepository.findById(incomeDto.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + incomeDto.categoryId()));
            income.setCategory(category);
        }

        return income;
    }
}
