package com.cafe.controllers;

import java.io.IOException;

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

import com.cafe.common.services.CommonServices;
import com.cafe.empAndUserServices.UserAndEmployeeServices;
import com.cafe.entities.Category;
import com.cafe.entities.Products;
import com.cafe.entities.UserDAO;
import com.cafe.fileService.FileService;
import com.cafe.loginService.LoginUserService;
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
	@Autowired
	CommonServices commonServices;
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	UserAndEmployeeServices andEmployeeServices;

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
			@RequestParam("cover-image") MultipartFile file, Model model, Products product) {

		// Handle validation errors
		if (bindingResult.hasErrors()) {
			return "admin/addProductPage";
		}

		// SAVING IMAGE
		try {
			fileService.getAndSetCategoryImage(coverImagePath, category, file);
		} catch (IOException e) {
			model.addAttribute("imageError", "Cover iamge is already existed");
			return "admin/addProductPage";
		}

		// Saving cate
		try {

			commonServices.CategorySaveService(category);
		} catch (Exception e) {
			bindingResult.rejectValue("catagoryName", "category.catagoryName",
					"a category with the same name is already exists");
			return "admin/addProductPage";
		}

		category = new Category();
		return "admin/addProductPage";
	}

	// Adding category
	@PostMapping("/addItem")
	public String addNewProduct(@Valid @ModelAttribute("products") Products products, BindingResult bindingResult,
			@RequestParam("itmimage") MultipartFile file, Model model) {
		// Handle validation errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("category", new Category());
			model.addAttribute("products", new Products());
			return "admin/addProductPage";
		}
		fileService.getAndSetProductImage(productImagePath, products, file);

		Category bycatagoryName = categoryRepo.findBycatagoryName(products.getCategoryName());
		products.setProductName(products.getProductName().toLowerCase());

		products.setCategory(bycatagoryName);

		// Saved item
		productsRepo.save(products);

		model.addAttribute("category", new Category());
		model.addAttribute("products", new Products());
		return "admin/addProductPage";
	}

	@GetMapping("/categories")
	public String getProductsList() {
		return "admin/menu";
	}

	@GetMapping("/selectedItemPage")
	public String getSelectedItemPage() {
		return "admin/SelectedCategoryItems";
	}

	@GetMapping("/menu")
	public String getMenuPage(Model model) {
		UserDAO loggedInUser = loginUserService.getLoggedInUser();

		model.addAttribute("role", loggedInUser.getROLE());
		return "common/menuPage";
	}

	@GetMapping("/manage-cate")
	public String manageCategory(Model model) {
		return "admin/manageItemCategory.html";
	}

	@GetMapping("/man-cateAndProd-page")
	public String getProdManagingPage(Products products, Category category) {
		return "admin/manageProdPage";
	}

	@PostMapping("/updateCategory")
	public String updateCate(@Valid @ModelAttribute("category") Category category, Products products,
			BindingResult bindingResult, @RequestParam("cover-image") MultipartFile file, Model model) {
		// Handle validation errors
		if (bindingResult.hasErrors()) {
			return "admin/manageProdPage";
		}

		commonServices.categoryUpdateService(category, coverImagePath, file);
		return "admin/manageProdPage";
	}

	@PostMapping("/manageItem")
	public String manageItem(@Valid @ModelAttribute("products") Products products, BindingResult bindingResult,
			@RequestParam("itmimage") MultipartFile file, Model model, Category category) {
		// Handle validation errors
		if (bindingResult.hasErrors()) {
			return "admin/addProductPage";
		}
		commonServices.updateProduct(products, file, productImagePath);
		return "admin/manageProdPage";
	}

	@GetMapping("/addEmp")
	public String addEmployee(UserDAO userDAO, Model model) {
		return "admin/addEmp";
	}

	@PostMapping("/registerEmp")
	public String addEmployeeToWork(@Valid UserDAO dao, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			return "admin/addEmp";
		}
		
		if(dao.getU_id() == 0) {
			model.addAttribute("error","user not exists");
			return "admin/addEmp";
		}
		model.addAttribute("error","");
		andEmployeeServices.addEmployee(dao);

		return "admin/addEmp";
	}
	
	@GetMapping("/viewCusOrEmp")
	public String getEmpOrCustomer() {
		return "admin/viewEmpORCustomers";
	}

}
