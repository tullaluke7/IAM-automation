package com.example.iam.repo;

import com.example.iam.model.Policy;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPolicyRepo {
  private final Map<String, Policy> policies = new ConcurrentHashMap<>();

  public InMemoryPolicyRepo() {
    // FinanceReport: FinanceManager OR department==Finance
    save(new Policy(
      "FinanceReport",
      List.of("FinanceManager"),
      Map.of("department","Finance")
    ));
    // ITSystem: Engineer OR department==IT
    save(new Policy(
      "ITSystem",
      List.of("Engineer"),
      Map.of("department","IT")
    ));
  }

  public Optional<Policy> get(String resource){ return Optional.ofNullable(policies.get(resource)); }
  public void save(Policy p){ policies.put(p.getResource(), p); }
  public List<Policy> all(){ return new ArrayList<>(policies.values()); }
}
