package net.vleven.ems_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.vleven.ems_backend.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	
	
	
}
