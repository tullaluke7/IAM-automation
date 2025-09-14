package com.example.iam.model;

import java.util.List;
import java.util.Map;

public class Policy {
  private String resource;               // e.g. FinanceReport
  private List<String> allowedRoles;     // RBAC
  private Map<String, String> abacMatch; // e.g. {"department":"Finance"}

  public Policy(){}
  public Policy(String resource, List<String> roles, Map<String,String> abac){
    this.resource=resource; this.allowedRoles=roles; this.abacMatch=abac;
  }

  // getters/setters
  public String getResource(){return resource;}
  public void setResource(String v){resource=v;}
  public List<String> getAllowedRoles(){return allowedRoles;}
  public void setAllowedRoles(List<String> v){allowedRoles=v;}
  public Map<String, String> getAbacMatch(){return abacMatch;}
  public void setAbacMatch(Map<String,String> v){abacMatch=v;}
}
