package com.example.iam.dto;

public class UserUpdateRequest {
  private String role;
  private String department;
  public String getRole(){return role;}
  public void setRole(String v){role=v;}
  public String getDepartment(){return department;}
  public void setDepartment(String v){department=v;}
}
