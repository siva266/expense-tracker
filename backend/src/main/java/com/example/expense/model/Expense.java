package com.example.expense.model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity @Table(name = "expenses")
public class Expense {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String title;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal amount;
    @Column(nullable = false) private String category;
    @Column(nullable = false) private LocalDate date;
    private String description;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; } public void setTitle(String t) { this.title = t; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal a) { this.amount = a; }
    public String getCategory() { return category; } public void setCategory(String c) { this.category = c; }
    public LocalDate getDate() { return date; } public void setDate(LocalDate d) { this.date = d; }
    public String getDescription() { return description; } public void setDescription(String d) { this.description = d; }
}
