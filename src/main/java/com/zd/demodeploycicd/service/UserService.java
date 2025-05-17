package com.zd.demodeploycicd.service;

import com.zd.demodeploycicd.dto.UserDTO;
import com.zd.demodeploycicd.entity.User;
import com.zd.demodeploycicd.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::toDTO);
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = toEntity(userDTO);
        User savedUser = userRepository.save(user);
        UserDTO savedDTO = toDTO(savedUser);
        savedDTO.setId(savedUser.getId());
        return savedDTO;
    }

    public Optional<UserDTO> updateUser(Long id, UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            return Optional.empty();
        }
        User user = existingUser.get();
        if (!user.getEmail().equals(userDTO.getEmail()) && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        User updatedUser = userRepository.save(user);
        UserDTO updatedDTO = toDTO(updatedUser);
        updatedDTO.setId(updatedUser.getId());
        return Optional.of(updatedDTO);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    private User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }
}