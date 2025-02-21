package net.vleven.ems_backend.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.vleven.ems_backend.model.User;
import net.vleven.ems_backend.repository.UserRepository;





@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		 List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
	                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))  // Add "ROLE_" manually
	                .collect(Collectors.toList());

		
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRoles().toArray(new String[0]))
				.authorities(authorities) //Add "ROLE_"
				.build();
	
	}
	
}
