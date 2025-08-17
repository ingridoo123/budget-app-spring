package com.example.budget_app.repository;

import com.example.budget_app.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    
    List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Income> findByCategoryId(Long categoryId);
    
    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.date BETWEEN :startDate AND :endDate")
    BigDecimal getTotalIncomeByDateRange(@Param("startDate") LocalDate startDate, 
                                        @Param("endDate") LocalDate endDate);
    
    @Query("SELECT SUM(i.amount) FROM Income i")
    BigDecimal getTotalIncome();
}
