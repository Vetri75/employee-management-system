package net.vleven.ems_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.vleven.ems_backend.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	boolean existsByEmail(String email);

	Optional<Employee> findByEmail(String email);
}
