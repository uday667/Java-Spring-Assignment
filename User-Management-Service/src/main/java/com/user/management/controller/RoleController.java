package com.user.management.controller;


import com.user.management.dto.RoleRequest;
import com.user.management.exception.UserNotFoundException;
import com.user.management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {
    private final UserService userService;
    
    public RoleController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    public ResponseEntity<Void> addRoleToUser( @RequestBody RoleRequest request)
            throws UserNotFoundException {
        userService.addRoleToUser(request.getUsername(), request.getRole());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> removeRoleFromUser(@RequestBody RoleRequest request)
            throws UserNotFoundException {
        userService.removeRoleFromUser(request.getUsername(), request.getRole());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}