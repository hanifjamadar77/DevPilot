package com.Devpilot.Backend.dto;

import com.Devpilot.Backend.entity.IndexStatus;

import java.time.Instant;
import java.util.UUID;

public record IndexStatusResponse (
    UUID repositoryId,
    IndexStatus indexStatus,
    int filesTotal,
    int filesProcessed,
    int chunkCount,
    Instant indexedAt,
    String errorMessage) {
}
