package com.user.management.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class RegisterRequest {
    @NotBlank
    private String username;
    
    @NotBlank
    @Size(min = 6)
    private String password;
    
    private String email;
    private String firstName;
    private String lastName;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}