package com.cafe.empAndUserServices;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.entities.Address;
import com.cafe.entities.UserDAO;
import com.cafe.repos.UserRepo;

import jakarta.validation.Valid;

@Service
public class UserAndEmployeeServices {
	@Autowired
	UserRepo userRepo;

	public UserDAO getUserByUserName(String username) {

		UserDAO byUsername = userRepo.findByUsername(username);
		if (byUsername == null) {
			return null;
		}

		byUsername.setCart(null);
		byUsername.setAddress(null);
		byUsername.setAttendence(null);
		byUsername.setOrders(null);

		return byUsername;
	}

	public UserDAO addEmployee(@Valid UserDAO dao) {

		UserDAO byUsername = userRepo.findById(dao.getU_id());
		byUsername.setUsername(dao.getUsername());
		byUsername.setSalary(dao.getSalary());
		byUsername.setPost(dao.getPost());
		byUsername.setROLE(dao.getROLE());

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate doj = LocalDate.parse(dao.getDOJ(), format);
		LocalDate dol = LocalDate.parse(dao.getDOL(), format);

		byUsername.setDOJ(doj.toString());
		byUsername.setDOL(dol.toString());
		
		UserDAO save = userRepo.save(byUsername);

		return save;
	}

	public List<UserDAO> getMyUsers(String sort) {
		
		System.out.println(sort);
		
		List<UserDAO> users;
		if(sort.equals("ALL")) {
			System.out.println("getting all");
			users = userRepo.findAll();
		}else {
			System.out.println("getting users");
			users = userRepo.findByROLE(sort);
			
		}
		
		// making ready the list to send to frontend
		List<UserDAO> listToSend = new ArrayList<UserDAO>();
		for(UserDAO userDAO : users) {
			
			userDAO.setCart(null);
			userDAO.setOrders(null);
			userDAO.setAttendence(null);
			userDAO.getAddress().setAdd_user(null);
			
			Address address = new Address();
			address = userDAO.getAddress();
			address.setAdd_user(null);
			
			listToSend.add(userDAO);
		}
		System.out.println(users.size());
		return listToSend;
		
	}

}
