package com.cafe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.entities.Category;
import com.cafe.entities.Products;
import com.cafe.repos.CategoryRepo;
import com.cafe.services.CommonServices;
import com.cafe.services.RegisterService;

@RestController
@RequestMapping("/common")
public class CommonRestController {
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	CommonServices commonServices;
	@Autowired
	RegisterService registerService;


	@GetMapping("/category")
	public ResponseEntity<String[]> categoryList() {
		List<Category> all = categoryRepo.findAll();
		String[] categories = new String[all.size()];
		int i = 0;
		for (Category category : all) {
			categories[i] = category.getCatagoryName();
			i++;
		}

		return ResponseEntity.ok().body(categories);
	}

	@GetMapping("/category-list")
	public ResponseEntity<List<Category>> fullCategoryList() {

		List<Category> categories = commonServices.getCategoryList();
		System.out.println("Category List: " + categories); // Log the categories
		return ResponseEntity.ok().body(categories);
	}

	@GetMapping("/getCommonCate/{id}")
	public ResponseEntity<List<Products>> getAllProductsOfTheSameCategory(@PathVariable long id) {
		System.out.println("Hello iam called");
		List<Products> selectedProducts = commonServices.getSelectedProducts(id);
		System.out.println("HEELO" + selectedProducts);
		return ResponseEntity.ok().body(selectedProducts);
	}

	@PostMapping("/saveToCart/{id}")
	public ResponseEntity<List<Products>> saveProductsToCart(@PathVariable long id, @RequestBody Products products) {
		List<Products> saveToCart = commonServices.saveToCart(id);
		return ResponseEntity.ok().body(saveToCart);
	}

	@GetMapping("/getCart")
	public ResponseEntity<List<Products>> getCart() {
		List<Products> saveToCart = commonServices.getCart();
		return ResponseEntity.ok().body(saveToCart);
	}

	@GetMapping("/manage-prod/{id}")
	public ResponseEntity<Category> getFullCategoryDataForManage(@PathVariable long id) {
		Category categoryForManage = commonServices.getCategoryForManage(id);
		return ResponseEntity.ok().body(categoryForManage);
	}

	@DeleteMapping("/deleteProduct")
	public ResponseEntity<List<Products>> deleteProduct(@RequestBody Products products) {
		List<Products> deleteProduct = commonServices.deleteProduct(products);
		return ResponseEntity.ok().body(deleteProduct);
	}

	

}
