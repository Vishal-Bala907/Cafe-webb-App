package com.cafe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe.entities.Address;

@Controller
@RequestMapping("/emp")
public class EmployeeViewController {
	@GetMapping("/userProfile")
	public String testUser() {
		return "employee/userProfile";
	}

	@GetMapping("/categories")
	public String getCategoryPage() {
		return "employee/categories";
	}

	@GetMapping("/selectedItemPage")
	public String getSelectedItemPage() {
		return "employee/SelectedCategoryItems";
	}

	@GetMapping("/menu")
	public String getMenuPage() {
		return "employee/menu";
	}

	@GetMapping("/cart")
	public String getCartPage(Address address) {
		return "employee/usercart";
	}

	@GetMapping("/offline-bill")
	public String getOfflineBilling() {
		return "employee/offliineBill";
	}
	
	@GetMapping("/error")
	public String errorPage() {
		return "employee/error";
	}
	
	@GetMapping("/emp-about")
	public String getAboutPage() {
		return "employee/employeeAbout";
	}
}
