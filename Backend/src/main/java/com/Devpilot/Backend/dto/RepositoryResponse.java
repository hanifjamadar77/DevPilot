package com.Devpilot.Backend.dto;

import java.time.Instant;
import java.util.UUID;

import com.Devpilot.Backend.entity.IndexStatus;
import com.fasterxml.jackson. annotation. JsonProperty;


public record RepositoryResponse(
        UUID id,
        Long githubRepoId,
        String owner,
        String name,
        String fullName,
        @JsonProperty("isPrivate") boolean isPrivate,
        String defaultBranch,
        String language,
        String htmlUrl,
        String description,
        IndexStatus indexStatus,
        Instant indexedAt,
        int chunkCount,
        int filesTotal,
        int filesProcessed,
        String errorMessage){

}