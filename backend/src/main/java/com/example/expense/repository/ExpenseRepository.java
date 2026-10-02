package com.example.expense.repository;
import com.example.expense.model.Expense;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT e FROM Expense e WHERE (:category IS NULL OR e.category = :category) " +
           "AND (:from IS NULL OR e.date >= :from) AND (:to IS NULL OR e.date <= :to)")
    Page<Expense> filter(@Param("category") String category, @Param("from") LocalDate from,
                         @Param("to") LocalDate to, Pageable pageable);

    @Query("SELECT e.category, SUM(e.amount) FROM Expense e GROUP BY e.category")
    List<Object[]> totalsByCategory();
}
