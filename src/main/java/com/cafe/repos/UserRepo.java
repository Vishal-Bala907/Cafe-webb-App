package com.cafe.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.entities.UserDAO;

public interface UserRepo extends JpaRepository<UserDAO, Long> {
	
	UserDAO findByUsername(String username);
	
	
	UserDAO findById(long id);
}
