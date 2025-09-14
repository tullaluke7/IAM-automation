package com.example.iam.dto;

public class LoginResponse {
  private String token;
  private String username;
  private String role;
  private String department;

  public LoginResponse(String token, String username, String role, String department){
    this.token=token; this.username=username; this.role=role; this.department=department;
  }

  public String getToken(){return token;}
  public String getUsername(){return username;}
  public String getRole(){return role;}
  public String getDepartment(){return department;}
}
