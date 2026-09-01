package com.Devpilot.Backend.controllers;

import com.Devpilot.Backend.dto.UserResponse;
import com.Devpilot.Backend.entity.User;
import com.Devpilot.Backend.repository.UserRepository;
import com.Devpilot.Backend.security.AppUserPrincipal;
import com.Devpilot.Backend.security.CurrentUser;
import com.Devpilot.Backend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CurrentUser currentUser;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @GetMapping("/login-url")
    public Map<String, String> loginUrl() {
        return Map.of("url", "/oauth2/authorization/github");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        try {
            // Try to get user from JWT token if provided
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                UUID userId = jwtTokenProvider.validateAndGetUserId(token);
                Optional<User> user = userRepository.findById(userId);
                if (user.isPresent()) {
                    User u = user.get();
                    return ResponseEntity.ok(new UserResponse(
                            u.getId(),
                            u.getGithubId(),
                            u.getGithubUsername(),
                            u.getDisplayName(),
                            u.getAvatarUrl()));
                }
            }
            
            // Fall back to session-based auth
            AppUserPrincipal principal = currentUser.require();
            User user = principal.getUser();
            return ResponseEntity.ok(new UserResponse(
                    user.getId(),
                    user.getGithubId(),
                    user.getGithubUsername(),
                    user.getDisplayName(),
                    user.getAvatarUrl()));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/check-session")
    public ResponseEntity<Map<String, Object>> checkSession(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                UUID userId = jwtTokenProvider.validateAndGetUserId(token);
                Optional<User> user = userRepository.findById(userId);
                if (user.isPresent()) {
                    return ResponseEntity.ok(Map.of(
                        "authenticated", true,
                        "user", user.get().getGithubUsername()
                    ));
                }
            }
            return ResponseEntity.ok(Map.of(
                "authenticated", false,
                "error", "No valid token"
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "authenticated", false,
                "error", e.getMessage()
            ));
        }
    }
}
