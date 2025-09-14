package com.example.iam.dto;
import jakarta.validation.constraints.NotBlank;

public class AccessCheckRequest {
  @NotBlank private String username;
  @NotBlank private String resource;

  public String getUsername(){return username;}
  public void setUsername(String v){username=v;}
  public String getResource(){return resource;}
  public void setResource(String v){resource=v;}
}
