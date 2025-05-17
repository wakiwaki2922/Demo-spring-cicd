package com.zd.demodeploycicd.controller;

import com.zd.demodeploycicd.dto.UserDTO;
import com.zd.demodeploycicd.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users")
    @ApiResponse(responseCode = "200", description = "List of users")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        logger.info("Fetching user with id: {}", id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("User not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User created")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO, UriComponentsBuilder ucb) {
        logger.info("Creating new user with email: {}", userDTO.getEmail());
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity
                .created(ucb.path("/api/users/{id}").buildAndExpand(createdUser.getId()).toUri())
                .body(createdUser);
    }

    @Operation(summary = "Update an existing user")
    @ApiResponse(responseCode = "200", description = "User updated")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        logger.info("Updating user with id: {}", id);
        return userService.updateUser(id, userDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("User not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Delete a user")
    @ApiResponse(responseCode = "204", description = "User deleted")
    @ApiResponse(responseCode = "404", description = "User not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with id: {}", id);
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        logger.warn("User not found with id: {}", id);
        return ResponseEntity.notFound().build();
    }
}