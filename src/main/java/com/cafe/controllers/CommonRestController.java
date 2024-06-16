package com.cafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.entities.Category;
import com.cafe.repos.CategoryRepo;

@RestController
@RequestMapping("/common")
public class CommonRestController {
	@Autowired
	CategoryRepo categoryRepo;
	
	@GetMapping("/category")
	public ResponseEntity<String[]> categoryList(){
		List<Category> all = categoryRepo.findAll();
		String[] categories = new String[all.size()];
		int i=0;
		for(Category category : all) {
			categories[i] = category.getCatagoryName();
			i++;
		}
		
		return ResponseEntity.ok().body(categories);
 	}

}
