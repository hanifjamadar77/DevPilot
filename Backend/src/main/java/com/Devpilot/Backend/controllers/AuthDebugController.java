package com.Devpilot.Backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthDebugController {

    @Value("${app.frontend-url}")
    private String frontendUrl;

    @Value("${app.cors.allowed-origins}")
    private String corsOrigins;

    @GetMapping("/debug")
    public ResponseEntity<Map<String, Object>> debug(HttpServletRequest request) {
        Map<String, Object> debug = new HashMap<>();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        debug.put("authenticated", auth != null && auth.isAuthenticated());
        debug.put("principal", auth != null ? auth.getPrincipal().toString() : "null");
        debug.put("authorities", auth != null ? auth.getAuthorities().toString() : "[]");
        
        debug.put("session_id", request.getSession(false) != null ? request.getSession().getId() : "no_session");
        debug.put("cookies", request.getHeader("Cookie") != null ? "present" : "missing");
        
        debug.put("frontend_url", frontendUrl);
        debug.put("cors_origins", corsOrigins);
        debug.put("request_origin", request.getHeader("Origin"));
        debug.put("referer", request.getHeader("Referer"));
        
        return ResponseEntity.ok(debug);
    }
}
