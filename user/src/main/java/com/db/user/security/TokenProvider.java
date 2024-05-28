package com.db.user.security;

import com.db.user.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class TokenProvider {

    private long expiresInMillis = 3600000;
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    private Key key;

    @PostConstruct
    protected void init() {
        // Base64 decode the secret key to ensure it is of the correct length
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * create JWT token.
     *
     * @param username username.
     * @param role     role.
     * @return token.
     */
    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role); // Add role to the token
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiresInMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * extract username from supplied token.
     *
     * @param token token value.
     * @return username.
     */
    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * extract user role from supplied token.
     *
     * @param token
     * @return
     */
    public String getRole(final String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().get("role", String.class);
    }

    /**
     * validate the token for authenticity and expiry.
     *
     * @param token token.
     * @return boolean flag.
     * @throws InvalidKeyException e.
     */
    public boolean validateToken(final String token) throws InvalidTokenException {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Expired or invalid JWT token" + e.getMessage());
        }
    }
}
