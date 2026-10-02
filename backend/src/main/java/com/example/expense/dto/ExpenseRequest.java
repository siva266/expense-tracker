package com.example.expense.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
public record ExpenseRequest(
    @NotBlank(message = "Title is required") @Size(max = 100, message = "Title must be at most 100 characters") String title,
    @NotNull(message = "Amount is required") @DecimalMin(value = "0.01", message = "Amount must be greater than 0") BigDecimal amount,
    @NotBlank(message = "Category is required") String category,
    @NotNull(message = "Date is required") @PastOrPresent(message = "Date cannot be in the future") LocalDate date,
    @Size(max = 255, message = "Description too long") String description) {}
