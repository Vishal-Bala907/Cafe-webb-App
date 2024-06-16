package com.cafe.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.entities.Category;
import com.cafe.entities.Products;
import com.cafe.fileService.FileService;
import com.cafe.repos.CategoryRepo;
import com.cafe.repos.ProductsRepo;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminViewController {
	@Autowired
	FileService fileService;
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	ProductsRepo productsRepo;

	@Value("${project.cover}")
	private String coverImagePath;

	@Value("${project.products}")
	private String productImagePath;

	@GetMapping("/test")
	public String testUser() {
		return "admin/test";
	}

	@GetMapping("/addProduct")
	public String getAddProductPage(Model model) {
		model.addAttribute("title", "Add product page");
		model.addAttribute("category", new Category());
		model.addAttribute("products", new Products());
		return "admin/addProductPage";
	}

	// Adding category
	@PostMapping("/addCategory")
	public String addNewCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult,
			@RequestParam("cover-image") MultipartFile file, Model model) {

		// Handle validation errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("category", new Category());
			model.addAttribute("products", new Products());
			return "admin/addProductPage";
		}

		Category andSetCategoryImage = fileService.getAndSetCategoryImage(coverImagePath, category, file);

		Products products = new Products();
		products.setDiscount(10);
		products.setProductImage("null");
		products.setProductName("none");
		products.setProductPrice(234);
		products.setSold(0);
		products.setCategory(andSetCategoryImage);

		List<Products> proList = new ArrayList<Products>();
		proList.add(products);

//		 saving a new category
		@Valid
		Category save = categoryRepo.save(category);
		// deleting extra data
		productsRepo.deleteByForeingKey(save.getC_Id());

		category = new Category();

		model.addAttribute("category", new Category());
		model.addAttribute("products", new Products());
		return "admin/addProductPage";
	}

	// Adding category
	@PostMapping("/addItem")
	public String addNewCategory(@Valid @ModelAttribute("products") Products products, BindingResult bindingResult,
			@RequestParam("itmimage") MultipartFile file, Model model) {
		// Handle validation errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("category", new Category());
			model.addAttribute("products", new Products());
			return "admin/addProductPage";
		}
		fileService.getAndSetProductImage(productImagePath, products, file);
		
		Category bycatagoryName = categoryRepo.findBycatagoryName(products.getCategoryName());
		
		products.setCategory(bycatagoryName);
		productsRepo.save(products);
		
		
		model.addAttribute("category", new Category());
		model.addAttribute("products", new Products());
		return "admin/addProductPage";
	}
}
