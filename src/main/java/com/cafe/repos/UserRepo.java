package com.cafe.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.entities.UserDAO;

public interface UserRepo extends JpaRepository<UserDAO, Long> {
	
	UserDAO findByUsername(String username);
	
	List<UserDAO> findByROLE(String role);
	
	UserDAO findById(long id);
}
