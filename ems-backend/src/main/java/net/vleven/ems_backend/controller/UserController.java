package net.vleven.ems_backend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import net.vleven.ems_backend.model.User;
import net.vleven.ems_backend.repository.UserRepository;
import net.vleven.ems_backend.dto.UserDto;
import net.vleven.ems_backend.model.Role;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Only ADMIN can get all users
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getRoles()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }


    // Only ADMIN can get user by ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(value -> ResponseEntity
        			.ok(new UserDto(value.getId(), value.getUsername(), value.getRoles())))
                   	.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserDto()));
    }


    // Only ADMIN can update user role
    @PutMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody String role) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            try {
                Role newRole = Role.valueOf(role.toUpperCase()); // Convert String to Enum
                user.setRoles(Set.of(newRole)); // Update role correctly
                userRepository.save(user);
                return ResponseEntity.ok("User role updated successfully");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid role: " + role);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }



    // Only ADMIN can delete a user
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    
    
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getRoles().stream().map(Enum::name).toList());
            return ResponseEntity.ok(userDto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    
    
}

