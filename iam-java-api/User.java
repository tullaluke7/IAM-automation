package com.example.iam.model;

public class User {
  private String username;
  private String password; // plain for demo
  private String role;     // e.g. FinanceManager, Analyst, Engineer
  private String department; // e.g. Finance, IT
  private UserStatus status = UserStatus.ACTIVE;

  public User() {}
  public User(String username, String password, String role, String department) {
    this.username = username; this.password = password; this.role = role; this.department = department;
  }

  // getters/setters
  public String getUsername(){return username;}
  public void setUsername(String v){username=v;}
  public String getPassword(){return password;}
  public void setPassword(String v){password=v;}
  public String getRole(){return role;}
  public void setRole(String v){role=v;}
  public String getDepartment(){return department;}
  public void setDepartment(String v){department=v;}
  public UserStatus getStatus(){return status;}
  public void setStatus(UserStatus v){status=v;}
}
