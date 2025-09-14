package com.example.iam.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwt;

  public JwtAuthFilter(JwtUtil jwt) {
    this.jwt = jwt;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws java.io.IOException, jakarta.servlet.ServletException {

    String auth = req.getHeader("Authorization");
    if (auth != null && auth.startsWith("Bearer ")) {
      String token = auth.substring(7);
      try {
        var jws = jwt.parse(token);
        Claims claims = jws.getBody();
        String username = claims.getSubject();
        String role = claims.get("role", String.class);
        var authToken = new UsernamePasswordAuthenticationToken(
            username, null,
            role != null ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)) : Collections.emptyList()
        );
        org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authToken);
      } catch (Exception ignored) {}
    }
    chain.doFilter(req, res);
  }
}
