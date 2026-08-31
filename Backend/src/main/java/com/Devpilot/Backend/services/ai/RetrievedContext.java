package com.Devpilot.Backend.services.ai;

import com.Devpilot.Backend.dto.CitationDto;

import java.util.List;

public record RetrievedContext(
        List<CitationDto> citations,
        String contextText) {
}
