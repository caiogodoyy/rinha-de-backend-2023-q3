package com.caio.rinha.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleValidationExceptions(Exception e) {
        return ResponseEntity.unprocessableEntity().body("Invalid input detected...\n" + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleSyntaxExceptions(Exception e) {
        return ResponseEntity.badRequest().body("Invalid input detected...\n" + e.getMessage());
    }

}
