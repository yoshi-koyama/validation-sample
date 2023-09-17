package com.validationsample.validation;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<Map<String, String>> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(Map.of("key", error.getField(), "message", error.getDefaultMessage()));
        }
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.BAD_REQUEST, "validation error", errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Map<String, String>> errors = new ArrayList<>();
        for (var violation : ex.getConstraintViolations()) {
            errors.add(Map.of("key", violation.getPropertyPath().toString(), "message", violation.getMessage()));
        }
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.BAD_REQUEST, "validation error", errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    public record ErrorResponse(HttpStatus status, String message, List<Map<String, String>> errors) {

    }
}
