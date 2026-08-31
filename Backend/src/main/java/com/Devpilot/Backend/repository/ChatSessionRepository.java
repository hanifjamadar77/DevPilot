package com.Devpilot.Backend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Devpilot.Backend.entity.ChatSession;

public interface ChatSessionRepository extends JpaRepository<ChatSession, UUID> {
    List<ChatSession> findByUserIdAndRepositoryIdOrderByCreatedAtDesc(UUID userId, UUID repositoryId);

    Optional<ChatSession> findByIdAndUserId(UUID id, UUID userId);
}