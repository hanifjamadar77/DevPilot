package com.Devpilot.Backend.security;

import com.Devpilot.Backend.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtTokenProvider {

    private final String secret;
    private final int expirationSeconds;

    public JwtTokenProvider(
            @Value("${app.jwt.secret:change-me-in-production-secret-key-64-chars-min}") String secret,
            @Value("${app.jwt.expiration:604800}") int expirationSeconds) {
        this.secret = secret;
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(User user) throws JOSEException {
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .claim("githubId", user.getGithubId())
                .claim("githubUsername", user.getGithubUsername())
                .claim("displayName", user.getDisplayName())
                .claim("avatarUrl", user.getAvatarUrl())
                .issueTime(Date.from(Instant.now()))
                .expirationTime(new Date(System.currentTimeMillis() + expirationSeconds * 1000L))
                .jwtID(UUID.randomUUID().toString())
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claims
        );

        signedJWT.sign(new MACSigner(secret));
        return signedJWT.serialize();
    }

    public UUID validateAndGetUserId(String token) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        if (!signedJWT.verify(new MACVerifier(secret))) {
            throw new JOSEException("Token verification failed");
        }

        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

        if (claims.getExpirationTime().before(Date.from(Instant.now()))) {
            throw new JOSEException("Token expired");
        }

        return UUID.fromString(claims.getSubject());
    }
}
