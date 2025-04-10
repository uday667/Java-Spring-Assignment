package com.user.management.dto;

import javax.validation.constraints.NotBlank;



public class RoleRequest {
    @NotBlank
    private String username;
    
    @NotBlank
    private String role;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public RoleRequest(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public RoleRequest() {
    }
}