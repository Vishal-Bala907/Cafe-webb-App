package com.cafe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cafe.entities.UserDAO;
import com.cafe.services.LoginUserService;

@Controller
public class MyErrorController implements ErrorController {
	@Autowired
	LoginUserService loginUserService;
	@GetMapping("/error")
	public String handleError() {
		System.out.println("why error here");
		UserDAO loggedInUser = loginUserService.getLoggedInUser();
		
		if(loggedInUser==null) {
			return "Public/error";
		}
		
		String role = loggedInUser.getROLE();
		if (role.equals("ROLE_ADMIN")) {
			return "redirect:/admin/error";
		} else if (role.equals("ROLE_CUSTOMER")) {
			return "redirect:/user/error";
		} else if (role.equals("ROLE_EMPLOYEE")) {
			return "redirect:/emp/error";
		}
		
		return "Public/index";
	}

	public String getErrorPath() {
		return "/error";
	}
}
