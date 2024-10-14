package com.cafe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.entities.Address;
import com.cafe.entities.UserDAO;
import com.cafe.repos.AttendenceRepo;
import com.cafe.repos.OrdersRepo;
import com.cafe.repos.UserRepo;
import com.cafe.services.FileService;
import com.cafe.services.LoginUserService;
import com.cafe.services.RegisterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class PublicViewController {

	@Autowired
	FileService servce;
	@Autowired
	RegisterService registerService;
	@Autowired
	UserRepo userRepo;
	@Autowired
	AttendenceRepo attendenceRepo;
	@Autowired
	OrdersRepo ordersRepo;
	@Autowired
	LoginUserService loginUserService;

	@Value("${project.profile}")
	private String path;

	@GetMapping("/")
	public String showIndexPage(Model model) {
		return "Public/index";
	}

	@GetMapping("/login")
	public String showLoginPage(Model model) {
		model.addAttribute("title", "Login Page");
		return "Public/login";
	}

	@PostMapping("/login")
	public String login(UserDAO dao, Model model) {
		model.addAttribute("title", "Login Page");
		return "Public/login";
	}

	@GetMapping("/signup-page")
	public String showSignUpPage(UserDAO userDAO, Model model) {
		model.addAttribute("title", "SignUp Page");
		return "Public/signup";
	}

	@PostMapping("/register")
	@Transactional
	public String registerUser(@Valid UserDAO userDAO, BindingResult bindingResult,
			@RequestParam("image") MultipartFile image, Model model) {
		model.addAttribute("title", "SignUp Page");

		// Step 1 -> check the file and data

		// Handle validation errors
		if (bindingResult.hasErrors()) {
			return "Public/signup";
		}

		try {
			UserDAO saveUser = registerService.saveUser(userDAO, path, image);
			userRepo.save(saveUser);
			// remove orders and attendence
			UserDAO savedUser = userRepo.findByUsername(saveUser.getUsername());
			ordersRepo.deleteByForeingKey(savedUser.getU_id());
			attendenceRepo.deleteByForeingKey(savedUser.getU_id());

		} catch (Exception w) {

		}
		// Save the user and file processing logic here

		return "Public/index"; // Redirect or show success page
	}

	/* ************************************ */

	@GetMapping("/uppage")
	public String updateProfilePage(UserDAO userDAO, Address address) {
		String role = loginUserService.getLoggedInUser().getROLE();
		if (role.equals("ROLE_ADMIN")) {
			return "admin/updateAdminProfile";
		} else if (role.equals("ROLE_CUSTOMER")) {

			return "user/updateProfilePage";
		} else if (role.equals("ROLE_EMPLOYEE")) {

			return "employee/updateProfilePage";
		}
		return "Public/signup";
	}

	@PostMapping("/updateProfile")
	public String updateProfile(@Valid UserDAO userDAO, BindingResult bindingResult,
			@RequestParam("image") MultipartFile image, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			UserDAO saveUser = registerService.updateUser(userDAO, path, image);
			userRepo.save(saveUser);
			this.logout(request, response);
		} catch (Exception w) {
			w.printStackTrace();

		}
//		}
		return "redirect:/login"; // Redirect or show success page
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("LOGGING OUT <--->");
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/login"; // You can redirect to any page after logout
	}

	@GetMapping("/profile")
	public String getProfile() {
		String role = loginUserService.getLoggedInUser().getROLE();
		if (role.equals("ROLE_ADMIN")) {
			return "redirect:/admin/profile";
		} else if (role.equals("ROLE_CUSTOMER")) {
			return "redirect:/user/userProfile";
		} else if (role.equals("ROLE_EMPLOYEE")) {
			return "redirect:/emp/userProfile";
		}

		return "Public/index";
	}

	@GetMapping("/about")
	public String getAboutPage() {
		return "Public/publicAbout";
	}

	/*
	 * @GetMapping("/error") public String handleError() {
	 * System.out.println("hello"); String role =
	 * loginUserService.getLoggedInUser().getROLE(); if (role.equals("ROLE_ADMIN"))
	 * { return "redirect:/admin/error"; } else if (role.equals("ROLE_CUSTOMER")) {
	 * return "redirect:/user/userProfile"; } else if (role.equals("ROLE_EMPLOYEE"))
	 * { return "redirect:/emp/userProfile"; } return "Public/index"; }
	 */

}
