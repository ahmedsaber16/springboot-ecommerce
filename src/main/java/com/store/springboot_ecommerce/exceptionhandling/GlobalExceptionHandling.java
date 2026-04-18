package com.store.springboot_ecommerce.exceptionhandling;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {
    public ResponseEntity<ErrorDetails> handleRuntimeException(RuntimeException e) {
        ErrorDetails error = new ErrorDetails();
        error.setMessage(e.getMessage());
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
    }
}