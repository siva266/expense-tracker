package com.example.expense.controller;
import com.example.expense.dto.ExpenseRequest;
import com.example.expense.model.Expense;
import com.example.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
@RestController @RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService service;
    public ExpenseController(ExpenseService service) { this.service = service; }

    @GetMapping
    public Page<Expense> list(@RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return service.list(category, from, to, page, size);
    }
    @GetMapping("/{id}") public Expense get(@PathVariable Long id) { return service.get(id); }
    @GetMapping("/summary") public Map<String, BigDecimal> summary() { return service.summary(); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Expense create(@Valid @RequestBody ExpenseRequest r) { return service.create(r); }
    @PutMapping("/{id}") public Expense update(@PathVariable Long id, @Valid @RequestBody ExpenseRequest r) { return service.update(id, r); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
