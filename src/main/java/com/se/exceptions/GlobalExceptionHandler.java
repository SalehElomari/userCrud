package com.se.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errors); // 400 Bad Request
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                404,
                "User Not Found",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(VersionConflictException.class)
    public ResponseEntity<ErrorResponse> handleVersionConflict(
            VersionConflictException ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                409,
                "Optimistic Lock Error",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(
            Exception ex, HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
                500,
                "Internal Error",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(500).body(error);
    }
}
