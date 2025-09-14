package com.example.iam.controller;

import com.example.iam.dto.AccessCheckRequest;
import com.example.iam.model.Policy;
import com.example.iam.model.User;
import com.example.iam.model.UserStatus;
import com.example.iam.repo.InMemoryPolicyRepo;
import com.example.iam.repo.InMemoryUserRepo;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccessController {

  private final InMemoryUserRepo users;
  private final InMemoryPolicyRepo policies;

  public AccessController(InMemoryUserRepo users, InMemoryPolicyRepo policies){
    this.users=users; this.policies=policies;
  }

  @PostMapping("/access-check")
  public ResponseEntity<?> canAccess(@RequestBody @Valid AccessCheckRequest req){
    var u = users.find(req.getUsername()).orElse(null);
    if (u == null || u.getStatus() != UserStatus.ACTIVE) {
      return ResponseEntity.ok(Map.of("access", false, "reason", "User not found or not active"));
    }
    var p = policies.get(req.getResource()).orElse(null);
    if (p == null) return ResponseEntity.ok(Map.of("access", false, "reason", "No policy for resource"));

    // RBAC
    if (p.getAllowedRoles()!=null && p.getAllowedRoles().contains(u.getRole())) {
      return ResponseEntity.ok(Map.of("access", true, "matched", "RBAC"));
    }
    // ABAC (simple equality matches, e.g., department)
    if (p.getAbacMatch()!=null && !p.getAbacMatch().isEmpty()) {
      boolean match = p.getAbacMatch().entrySet().stream().allMatch(e -> {
        String key = e.getKey(); String val = e.getValue();
        return switch (key) {
          case "department" -> val.equals(u.getDepartment());
          default -> false;
        };
      });
      if (match) return ResponseEntity.ok(Map.of("access", true, "matched", "ABAC"));
    }
    return ResponseEntity.ok(Map.of("access", false, "reason", "Policy not satisfied"));
  }

  @GetMapping("/policies")
  public ResponseEntity<?> listPolicies(){ return ResponseEntity.ok(policies.all()); }
}
