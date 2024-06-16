package com.cafe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.entities.UserDAO;
import com.cafe.fileService.FileService;
import com.cafe.registerService.RegisterService;
import com.cafe.repos.AttendenceRepo;
import com.cafe.repos.OrdersRepo;
import com.cafe.repos.UserRepo;

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
	public String login(UserDAO dao  ,Model model) {
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
}
