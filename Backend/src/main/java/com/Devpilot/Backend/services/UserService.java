package com.Devpilot.Backend.services;

import com.Devpilot.Backend.entity.User;
import com.Devpilot.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;
    public final TextEncryptor tokenEncryptor;

    @Transactional(readOnly = true)
    public User requiredByIdUser(UUID id) {
        return userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found!"));
    }

    public String decryptAccessToken(User user) {
        return tokenEncryptor.decrypt(user.getAccessToken());
    }

    private static Long toLong(Object value) {
        if(value instanceof Number number) {
            return number.longValue();
        }
        return Long.parseLong(String.valueOf(value));
    }
}
