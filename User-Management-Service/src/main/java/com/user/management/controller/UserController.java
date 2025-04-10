package com.user.management.controller;

import com.user.management.dto.RegisterRequest;
import com.user.management.dto.UserDTO;
import com.user.management.exception.UserNotFoundException;
import com.user.management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> registerUser( @RequestBody RegisterRequest request) {
        UserDTO userDTO = userService.registerUser(request);
        System.out.println("Returning DTO: " + userDTO); // Temporary debug
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or (authentication.principal == @userRepository.findById(#id).orElseThrow().username)")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws UserNotFoundException, AccessDeniedException {
        // No security annotation - we'll handle security in service layer
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }
    
    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or (authentication.principal == @userRepository.findById(#id).orElseThrow().username)")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @Validated @RequestBody UserDTO userDTO) throws UserNotFoundException {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or (authentication.principal == @userRepository.findById(#id).orElseThrow().username)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}