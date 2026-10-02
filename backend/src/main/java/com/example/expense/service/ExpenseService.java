package com.example.expense.service;
import com.example.expense.dto.ExpenseRequest;
import com.example.expense.exception.ResourceNotFoundException;
import com.example.expense.model.Expense;
import com.example.expense.repository.ExpenseRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
@Service
public class ExpenseService {
    private final ExpenseRepository repo;
    public ExpenseService(ExpenseRepository repo) { this.repo = repo; }

    public Page<Expense> list(String category, LocalDate from, LocalDate to, int page, int size) {
        String cat = (category == null || category.isBlank()) ? null : category;
        return repo.filter(cat, from, to, PageRequest.of(page, size, Sort.by("date").descending().and(Sort.by("id").descending())));
    }
    public Expense get(Long id) {
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found with id " + id));
    }
    public Expense create(ExpenseRequest r) { return repo.save(apply(new Expense(), r)); }
    public Expense update(Long id, ExpenseRequest r) { return repo.save(apply(get(id), r)); }
    public void delete(Long id) { repo.delete(get(id)); }
    public Map<String, BigDecimal> summary() {
        Map<String, BigDecimal> m = new LinkedHashMap<>();
        repo.totalsByCategory().forEach(row -> m.put((String) row[0], (BigDecimal) row[1]));
        return m;
    }
    private Expense apply(Expense e, ExpenseRequest r) {
        e.setTitle(r.title()); e.setAmount(r.amount()); e.setCategory(r.category());
        e.setDate(r.date()); e.setDescription(r.description());
        return e;
    }
}
