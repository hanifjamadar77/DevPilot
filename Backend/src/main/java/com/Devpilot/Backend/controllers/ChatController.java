package com.Devpilot.Backend.controllers;

import java.util.List;
import java.util.UUID;

import com.Devpilot.Backend.dto.ChatMessageRequest;
import com.Devpilot.Backend.dto.ChatMessageResponse;
import com.Devpilot.Backend.dto.ChatSessionResponse;
import com.Devpilot.Backend.dto.CreateChatSessionRequest;
import com.Devpilot.Backend.security.CurrentUser;
import com.Devpilot.Backend.services.ChatService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final CurrentUser currentUser;
    private final ChatService chatService;

    @PostMapping("/sessions")
    public ResponseEntity<ChatSessionResponse> createSession(
            @Valid @RequestBody CreateChatSessionRequest request) {
        UUID userId = currentUser.require().getId();
        return ResponseEntity.ok(chatService.createSession(userId, request));
    }

    @GetMapping("/sessions")
    public List<ChatSessionResponse> listSessions(@RequestParam UUID repositoryId) {
        UUID userId = currentUser.require().getId();
        return chatService.listSessions(userId, repositoryId);
    }

    @GetMapping("/sessions/{id}")
    public List<ChatMessageResponse> getMessages(@PathVariable UUID id) {
        UUID userId = currentUser.require().getId();
        return chatService.getMessages(userId, id);
    }

    @PostMapping(value = "/sessions/{id}/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sendMessage(
            @PathVariable UUID id,
            @Valid @RequestBody ChatMessageRequest request) {
        UUID userId = currentUser.require().getId();
        return chatService.streamReply(userId, id, request.content());
    }
}
