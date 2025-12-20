package com.food.pagamentos.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ErrorResponseDto(Integer status, Map<String, String> errors, String message, LocalDateTime timestamp) {
}
