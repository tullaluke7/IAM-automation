package com.example.iam.dto;
import jakarta.validation.constraints.NotBlank;

public class UserCreateRequest {
  @NotBlank private String username;
  @NotBlank private String password;
  @NotBlank private String role;
  @NotBlank private String department;

  public String getUsername(){return username;}
  public void setUsername(String v){username=v;}
  public String getPassword(){return password;}
  public void setPassword(String v){password=v;}
  public String getRole(){return role;}
  public void setRole(String v){role=v;}
  public String getDepartment(){return department;}
  public void setDepartment(String v){department=v;}
}
