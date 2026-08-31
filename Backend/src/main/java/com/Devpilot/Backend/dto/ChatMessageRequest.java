package com.Devpilot.Backend.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequest(
        @NotBlank String content) {
}
