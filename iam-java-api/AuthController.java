package com.example.iam.controller;

import com.example.iam.dto.LoginRequest;
import com.example.iam.dto.LoginResponse;
import com.example.iam.model.User;
import com.example.iam.model.UserStatus;
import com.example.iam.repo.InMemoryUserRepo;
import com.example.iam.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final InMemoryUserRepo users;
  private final JwtUtil jwt;

  public AuthController(InMemoryUserRepo users, JwtUtil jwt) {
    this.users = users; this.jwt = jwt;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginRequest req) {
    var user = users.find(req.getUsername())
            .filter(u -> u.getPassword().equals(req.getPassword()))
            .filter(u -> u.getStatus() == UserStatus.ACTIVE)
            .orElse(null);
    if (user == null) return ResponseEntity.status(401).body(Map.of("error","Invalid credentials or terminated user"));

    String token = jwt.generate(
      user.getUsername(),
      Map.of("role", user.getRole(), "department", user.getDepartment()),
      1000L * 60 * 60 // 1 hour
    );
    return ResponseEntity.ok(new LoginResponse(token, user.getUsername(), user.getRole(), user.getDepartment()));
  }
}
