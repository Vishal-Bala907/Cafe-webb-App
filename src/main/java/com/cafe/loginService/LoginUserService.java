package com.cafe.loginService;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cafe.entities.UserDAO;
import com.cafe.repos.UserRepo;

@Service
public class LoginUserService implements UserDetailsService {

	@Autowired
	UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserDAO byUsername = repo.findByUsername(username);
		if (byUsername == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new User(username, byUsername.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(byUsername.getROLE())));
//		return null;
	}

}
