package com.zd.demodeploycicd.service;

import com.zd.demodeploycicd.dto.UserDTO;
import com.zd.demodeploycicd.entity.User;
import com.zd.demodeploycicd.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");

        testUserDTO = new UserDTO();
        testUserDTO.setId(1L);
        testUserDTO.setName("Test User");
        testUserDTO.setEmail("test@example.com");
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

        List<UserDTO> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(testUser.getId());
        assertThat(result.get(0).getName()).isEqualTo(testUser.getName());
        assertThat(result.get(0).getEmail()).isEqualTo(testUser.getEmail());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_WithExistingId_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<UserDTO> result = userService.getUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(testUser.getId());
        assertThat(result.get().getName()).isEqualTo(testUser.getName());
        assertThat(result.get().getEmail()).isEqualTo(testUser.getEmail());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_WithNonExistingId_ShouldReturnEmpty() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.getUserById(99L);

        assertThat(result).isEmpty();
        verify(userRepository).findById(99L);
    }

    @Test
    void createUser_WithNewEmail_ShouldCreateUser() {
        when(userRepository.existsByEmail(testUserDTO.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDTO result = userService.createUser(testUserDTO);

        assertThat(result.getName()).isEqualTo(testUserDTO.getName());
        assertThat(result.getEmail()).isEqualTo(testUserDTO.getEmail());
        verify(userRepository).existsByEmail(testUserDTO.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_WithExistingEmail_ShouldThrowException() {
        when(userRepository.existsByEmail(testUserDTO.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser(testUserDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already exists");

        verify(userRepository).existsByEmail(testUserDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_WithExistingId_ShouldUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        UserDTO updatedDTO = new UserDTO();
        updatedDTO.setId(1L);
        updatedDTO.setName("Updated Name");
        updatedDTO.setEmail("updated@example.com");
        
        when(userRepository.existsByEmail("updated@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        Optional<UserDTO> result = userService.updateUser(1L, updatedDTO);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Updated Name");
        assertThat(result.get().getEmail()).isEqualTo("updated@example.com");
        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmail("updated@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_WithNonExistingId_ShouldReturnEmpty() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.updateUser(99L, testUserDTO);

        assertThat(result).isEmpty();
        verify(userRepository).findById(99L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_WithExistingEmail_ShouldThrowException() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmail("old@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByEmail(testUserDTO.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> userService.updateUser(1L, testUserDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email already exists");

        verify(userRepository).findById(1L);
        verify(userRepository).existsByEmail(testUserDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser_WithExistingId_ShouldReturnTrue() {
        when(userRepository.existsById(1L)).thenReturn(true);

        boolean result = userService.deleteUser(1L);

        assertThat(result).isTrue();
        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void deleteUser_WithNonExistingId_ShouldReturnFalse() {
        when(userRepository.existsById(99L)).thenReturn(false);

        boolean result = userService.deleteUser(99L);

        assertThat(result).isFalse();
        verify(userRepository).existsById(99L);
        verify(userRepository, never()).deleteById(any());
    }
}