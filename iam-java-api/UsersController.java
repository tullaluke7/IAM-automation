package com.example.iam.controller;

import com.example.iam.dto.UserCreateRequest;
import com.example.iam.dto.UserUpdateRequest;
import com.example.iam.model.User;
import com.example.iam.repo.InMemoryUserRepo;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

  private final InMemoryUserRepo users;

  public UsersController(InMemoryUserRepo users){ this.users = users; }

  @GetMapping
  public ResponseEntity<?> list(){ return ResponseEntity.ok(users.findAll()); }

  // JOINER
  @PostMapping
  public ResponseEntity<?> create(@RequestBody @Valid UserCreateRequest req){
    if (users.find(req.getUsername()).isPresent()) {
      return ResponseEntity.badRequest().body(java.util.Map.of("error","User already exists"));
    }
    var u = new User(req.getUsername(), req.getPassword(), req.getRole(), req.getDepartment());
    users.save(u);
    return ResponseEntity.ok(u);
  }

  // MOVER
  @PutMapping("/{username}")
  public ResponseEntity<?> update(@PathVariable String username, @RequestBody @Valid UserUpdateRequest req){
    var u = users.find(username).orElse(null);
    if (u == null) return ResponseEntity.notFound().build();
    if (req.getRole()!=null) u.setRole(req.getRole());
    if (req.getDepartment()!=null) u.setDepartment(req.getDepartment());
    users.save(u);
    return ResponseEntity.ok(u);
  }

  // LEAVER
  @DeleteMapping("/{username}")
  public ResponseEntity<?> terminate(@PathVariable String username){
    var u = users.find(username).orElse(null);
    if (u == null) return ResponseEntity.notFound().build();
    users.delete(username);
    return ResponseEntity.ok(java.util.Map.of("message","User terminated"));
  }
}
