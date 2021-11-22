package com.formacion.back.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.formacion.back.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("Select u from User u where LOWER(u.username) LIKE LOWER (?1)")
	User findByUsername(String username);
	
	@Query("Select u from User u where LOWER(u.email) LIKE LOWER (?1)")
	User findByEmail(String email);
	
	@Query("Select u.username from User u where LOWER(u.username) LIKE LOWER (?1) and LOWER(u.password) LIKE LOWER(?2)")
	User login(String username, String password);

}
