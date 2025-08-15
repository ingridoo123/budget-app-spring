package com.example.budget_app.excepiton;


import com.example.budget_app.dto.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(
                LocalDateTime.now(),
                "An unexpected error occurred :(",
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
