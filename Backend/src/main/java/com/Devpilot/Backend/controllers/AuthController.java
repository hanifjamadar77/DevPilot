package com.Devpilot.Backend.controllers;

import com.Devpilot.Backend.dto.UserResponse;
import com.Devpilot.Backend.entity.User;
import com.Devpilot.Backend.security.AppUserPrincipal;
import com.Devpilot.Backend.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final CurrentUser currentUser;

    @GetMapping("/login-url")
    public Map<String, String> loginUrl() {
        return Map.of("url", "/oauth2/authorization/github");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        AppUserPrincipal principal = currentUser.require();
        User user = principal.getUser();
        return ResponseEntity.ok(new UserResponse(
                user.getId(),
                user.getGithubId(),
                user.getGithubUsername(),
                user.getDisplayName(),
                user.getAvatarUrl()));
    }

    @GetMapping("/check-session")
    public ResponseEntity<Map<String, Object>> checkSession(jakarta.servlet.http.HttpServletRequest request) {
        try {
            AppUserPrincipal principal = currentUser.require();
            return ResponseEntity.ok(Map.of(
                "authenticated", true,
                "user", principal.getUser().getGithubUsername()
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "authenticated", false,
                "error", e.getMessage()
            ));
        }
    }
}
