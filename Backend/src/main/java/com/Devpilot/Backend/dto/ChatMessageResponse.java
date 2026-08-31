package com.Devpilot.Backend.dto;

import com.Devpilot.Backend.entity.MessageRole;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChatMessageResponse(
        UUID id,
        MessageRole role,
        String content,
        List<CitationDto> citations,
        Instant createdAt) {
}
