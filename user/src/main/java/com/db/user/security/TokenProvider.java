package com.db.user.security;

import com.db.user.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenProvider {

    private long expiresInMillis = 3600000; // 1h
    private String key = "key";

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }
    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role); // Add role to the token
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiresInMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().parseClaimsJws(token).getBody().getSubject();
    }

    public String getRole(String token) {
        return Jwts.parser().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public boolean validateToken(String token) throws InvalidKeyException {
        try {
            Jws<Claims> claims = Jwts.parser().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Expired or invalid JWT token");
        }
    }
}
