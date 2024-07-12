package com.insignia.security;

import com.insignia.constants.SecretKeyConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userName, Integer expireTIme) {
        return createToken(Collections.emptyMap(), userName, expireTIme);
    }

    private String createToken(Map<String, Object> claims, String subject,
                               Integer expireTIme) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + SecretKeyConstant.EXPIRY_DURATION * expireTIme))
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token, String userName) {
        final String username = extractUsername(token);
        return (username.equals(userName) && !isTokenExpired(token));
    }
}