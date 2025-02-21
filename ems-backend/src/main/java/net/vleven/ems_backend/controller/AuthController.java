package net.vleven.ems_backend.controller;


import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


import lombok.RequiredArgsConstructor;
import net.vleven.ems_backend.model.User;
import net.vleven.ems_backend.repository.UserRepository;
import net.vleven.ems_backend.security.JwtUtil;
import net.vleven.ems_backend.model.Role;




@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
	    Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

	    if (existingUser.isPresent()) {
	        return ResponseEntity.badRequest().body(Map.of("message", "Username already taken"));
	    }

	    // ✅ Encode the password before saving
	    user.setPassword(passwordEncoder.encode(user.getPassword())); 

	    if (user.getRoles() == null || user.getRoles().isEmpty()) {
	        user.setRoles(Collections.singleton(Role.EMPLOYEE));
	    }

	    userRepository.save(user);
	    
	    return ResponseEntity.ok(Map.of("message", "User registered successfully"));
	}




	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
	    Optional<User> dbUser = userRepository.findByUsername(user.getUsername());

	    if (dbUser.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
	    }

	    System.out.println("🔹 Login attempt for user: " + user.getUsername());

	    // ✅ Print hashed password stored in DB
	    System.out.println("🔹 Stored password: " + dbUser.get().getPassword());
	    System.out.println("🔹 Entered password: " + user.getPassword());

	    // ✅ Compare passwords
	    if (!passwordEncoder.matches(user.getPassword(), dbUser.get().getPassword())) {
	        System.out.println("❌ Password mismatch!");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
	    }

	    String token = jwtUtil.generateToken(user.getUsername(), dbUser.get().getRoles());
	    return ResponseEntity.ok(Map.of("token", token));
	}


	
}
