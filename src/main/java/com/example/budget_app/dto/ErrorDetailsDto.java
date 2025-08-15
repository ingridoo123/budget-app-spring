package com.example.budget_app.dto;

import java.time.LocalDateTime;

public record ErrorDetailsDto(
    LocalDateTime timestamp,
    String message,
    String details
) {

}
