package com.cafe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe.entities.Address;

@Controller
@RequestMapping("/user")
public class UserViewController {
	
	@GetMapping("/userProfile")
	public String testUser() {
		return "/user/userProfile";
	}
	
	@GetMapping("/categories")
	public String getCategoryPage() {
		return "user/categories";
	}
	
	@GetMapping("/selectedItemPage")
	public String getSelectedItemPage() {
		return "user/SelectedCategoryItems";
	}
	
	@GetMapping("/menu")
	public String getMenuPage() {
		return "user/menu";
	}
	
	@GetMapping("/cart")
	public String getCartPage(Address address) {
		return "user/usercart";
	}

}
