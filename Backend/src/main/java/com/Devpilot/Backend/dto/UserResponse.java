package com.Devpilot.Backend.dto;

public record UserResponse(
        java.util.UUID id,
        Long githubId,
        String githubUsername,
        String displayName,
        String avatarUrl
)
{
}