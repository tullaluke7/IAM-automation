package com.example.iam.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
  private final Key key;
  public JwtUtil(@Value("${app.jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String generate(String subject, Map<String, Object> claims, long ttlMillis) {
    long now = System.currentTimeMillis();
    return Jwts.builder()
            .setSubject(subject)
            .addClaims(claims)
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + ttlMillis))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  public Jws<Claims> parse(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
  }
}
