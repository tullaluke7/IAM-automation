package com.example.iam.dto;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
  @NotBlank private String username;
  @NotBlank private String password;
  public String getUsername(){return username;}
  public void setUsername(String v){username=v;}
  public String getPassword(){return password;}
  public void setPassword(String v){password=v;}
}
