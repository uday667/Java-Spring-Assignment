package com.user.management.service;


import com.user.management.dto.RegisterRequest;
import com.user.management.dto.UserDTO;
import com.user.management.entity.User;
import com.user.management.event.UserEventPublisher;
import com.user.management.exception.UserNotFoundException;
import com.user.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private  ModelMapper modelMapper;
    private  PasswordEncoder passwordEncoder;
    private  UserEventPublisher eventPublisher;
    @Autowired
    public UserServiceImpl(ModelMapper modelMapper,
                            PasswordEncoder passwordEncoder,
                            UserEventPublisher eventPublisher,
                           UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.userRepository = userRepository;

    }
    @Override
    @Transactional
    public UserDTO registerUser(RegisterRequest request) {
        User user = new User();
        // Manual mapping from request to entity
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.getRoles().add("USER");

        User savedUser = userRepository.save(user);
        eventPublisher.publishUserEvent(savedUser, "USER_REGISTERED");

        // Manual mapping from entity to DTO
        UserDTO dto = new UserDTO();
        dto.setId(savedUser.getId());
        dto.setUsername(savedUser.getUsername());
        dto.setEmail(savedUser.getEmail());
        dto.setFirstName(savedUser.getFirstName());
        dto.setLastName(savedUser.getLastName());
        dto.setRoles(savedUser.getRoles());
        dto.setCreatedAt(savedUser.getCreatedAt());
        dto.setUpdatedAt(savedUser.getUpdatedAt());

        return dto;
    }

    @Override
    public UserDTO getUserById(Long id) throws UserNotFoundException, AccessDeniedException {
        // Get the requested user
        User requestedUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        // Create and populate DTO manually
        UserDTO userDTO = new UserDTO();
        userDTO.setId(requestedUser.getId());
        userDTO.setUsername(requestedUser.getUsername());
        userDTO.setEmail(requestedUser.getEmail());
        userDTO.setFirstName(requestedUser.getFirstName());
        userDTO.setLastName(requestedUser.getLastName());
        userDTO.setRoles(requestedUser.getRoles());
        userDTO.setCreatedAt(requestedUser.getCreatedAt());
        userDTO.setUpdatedAt(requestedUser.getUpdatedAt());

        return userDTO;
    }
    public boolean isOwner(String username, Long userId) {
        return userRepository.findByUsername(username)
                .map(user -> user.getId().equals(userId))
                .orElse(false);
    }
    @Override
    public UserDTO getUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) throws UserNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        modelMapper.map(userDTO, existingUser);
        existingUser.setId(id); // Ensure ID remains unchanged

        User updatedUser = userRepository.save(existingUser);
        eventPublisher.publishUserEvent(updatedUser, "USER_UPDATED");

        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
        eventPublisher.publishUserEvent(user, "USER_DELETED");
    }

    @Override
    @Transactional
    public void addRoleToUser(String username, String role) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        user.getRoles().add(role.toUpperCase());
        userRepository.save(user);
        eventPublisher.publishUserEvent(user, "ROLE_ADDED");
    }

    @Override
    @Transactional
    public void removeRoleFromUser(String username, String role) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        user.getRoles().remove(role.toUpperCase());
        userRepository.save(user);
        eventPublisher.publishUserEvent(user, "ROLE_REMOVED");
    }
}