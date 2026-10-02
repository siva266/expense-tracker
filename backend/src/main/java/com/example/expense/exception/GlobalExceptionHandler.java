package com.example.expense.exception;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Map<String, Object> body(HttpStatus s, String msg) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("timestamp", LocalDateTime.now().toString()); m.put("status", s.value()); m.put("message", msg);
        return m;
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> notFound(ResourceNotFoundException e) {
        return ResponseEntity.status(404).body(body(HttpStatus.NOT_FOUND, e.getMessage()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalid(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(f -> errors.put(f.getField(), f.getDefaultMessage()));
        Map<String, Object> b = body(HttpStatus.BAD_REQUEST, "Validation failed"); b.put("errors", errors);
        return ResponseEntity.badRequest().body(b);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> other(Exception e) {
        return ResponseEntity.status(500).body(body(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " + e.getMessage()));
    }
}
