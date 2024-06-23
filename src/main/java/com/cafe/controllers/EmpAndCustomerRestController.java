package com.cafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.empAndUserServices.UserAndEmployeeServices;
import com.cafe.entities.UserDAO;



@RestController
@RequestMapping("/empAndCus")
public class EmpAndCustomerRestController {
	@Autowired
	UserAndEmployeeServices andEmployeeServices;
	
	@GetMapping("/userByName/{username}")
	public UserDAO getUserDetailsByName(@PathVariable String username,Model model) {
		UserDAO userByUserName = andEmployeeServices.getUserByUserName(username);
		if(userByUserName == null) {
			return null;
		}
		return  userByUserName;
	}
	
	@GetMapping("/getEmpOrCus/{sort}")
	public ResponseEntity<List<UserDAO>> getEmpOrCustomers(@PathVariable String sort){
		
		List<UserDAO> myUsers = andEmployeeServices.getMyUsers(sort);
		return ResponseEntity.ok().body(myUsers);
	}
}
