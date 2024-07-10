package com.cafe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe.loginService.LoginUserService;

@Controller
@RequestMapping("/foo")
public class FooterViewController {
	@Autowired
	LoginUserService loginUserService;

	@GetMapping("/menu")
	public String getMenuFromFooter() {
		String role = loginUserService.getLoggedInUser().getROLE();
		if (role.equals("ROLE_ADMIN")) {
			return "redirect:/admin/menu";
		} else if (role.equals("ROLE_CUSTOMER")) {
			return "redirect:/user/menu";
		} else if (role.equals("ROLE_EMPLOYEE")) {
			return "redirect:/emp/menu";
		}
		return "redirect:/login";
	}
	
	@GetMapping("/categories")
	public String getCategoriesFromFooter() {
		String role = loginUserService.getLoggedInUser().getROLE();
		if (role.equals("ROLE_ADMIN")) {
			return "redirect:/admin/categories";
		} else if (role.equals("ROLE_CUSTOMER")) {
			return "redirect:/user/categories";
		} else if (role.equals("ROLE_EMPLOYEE")) {
			return "redirect:/emp/categories";
		}
		return "redirect:/login";
	}
	
	
	@GetMapping("/login")
	public String getFooterLogin() {
		return "redirect:/login";
	}

	@GetMapping("/logout")
	public String getFooterLogout() {
		return "redirect:/logout";
	}
	
	@GetMapping("/signup")
	public String getFooterSignup() {
		return "redirect:/register";
	}
}
