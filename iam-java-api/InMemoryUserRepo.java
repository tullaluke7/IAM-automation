package com.example.iam.repo;

import com.example.iam.model.User;
import com.example.iam.model.UserStatus;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepo {
  private final Map<String, User> users = new ConcurrentHashMap<>();

  public InMemoryUserRepo() {
    // seed demo users (password: password)
    save(new User("alice","password","FinanceManager","Finance"));
    save(new User("bob","password","Analyst","Finance"));
    save(new User("carlos","password","Engineer","IT"));
  }

  public List<User> findAll(){ return new ArrayList<>(users.values()); }
  public Optional<User> find(String username){ return Optional.ofNullable(users.get(username)); }
  public void save(User u){ users.put(u.getUsername(), u); }
  public void delete(String username){
    var u = users.get(username);
    if (u != null) u.setStatus(UserStatus.TERMINATED);
  }
}
