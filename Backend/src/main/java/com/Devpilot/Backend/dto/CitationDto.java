package com.Devpilot.Backend.dto;

public record CitationDto (
    String filePath,
    Integer startLine,
    Integer endLine,
    String language) {
}
