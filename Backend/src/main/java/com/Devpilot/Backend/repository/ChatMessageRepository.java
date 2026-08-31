package com.Devpilot.Backend.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Devpilot.Backend.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findBySessionIdOrderByCreatedAtAsc(UUID sessionId);
}