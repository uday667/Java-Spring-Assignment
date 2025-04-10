package com.user.management.service;

import com.user.management.dto.RegisterRequest;
import com.user.management.dto.UserDTO;
import com.user.management.exception.UserNotFoundException;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface UserService {
    UserDTO registerUser(RegisterRequest request);
    UserDTO getUserById(Long id) throws UserNotFoundException, AccessDeniedException;


    UserDTO getUserByUsername(String username) throws UserNotFoundException;
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;
    void addRoleToUser(String username, String role) throws UserNotFoundException;
    void removeRoleFromUser(String username, String role) throws UserNotFoundException;
}
