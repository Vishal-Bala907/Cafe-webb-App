package com.cafe.common.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.entities.Address;
import com.cafe.entities.Category;
import com.cafe.entities.Products;
import com.cafe.entities.UserBag;
import com.cafe.entities.UserDAO;
import com.cafe.fileService.FileService;
import com.cafe.loginService.LoginUserService;
import com.cafe.repos.BagRepo;
import com.cafe.repos.CategoryRepo;
import com.cafe.repos.ProductsRepo;

import jakarta.validation.Valid;

@Service
public class CommonServices {
	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	ProductsRepo productsRepo;
	@Autowired
	LoginUserService loginUserService;
	@Autowired
	BagRepo bagRepo;
	@Autowired
	FileService fileService;

	@Value("${project.delete.cover}")
	String deleteCoverPath;

	@Value("${project.delete.products}")
	String deleteProductPath;

	public Category CategorySaveService(Category category) throws Exception {

		Category bycatagoryName = categoryRepo.findBycatagoryName(category.getCatagoryName());
		if (bycatagoryName != null) {
			throw new Exception("a category with the same name is already exists");
		} else {
//			Products products = new Products();
//			products.setDiscount(10);
//			products.setProductImage("null");
//			products.setProductName("none");
//			products.setProductPrice(234);
//			products.setSold(0);
//			products.setCategory(category);
//
//			List<Products> proList = new ArrayList<Products>();
//			proList.add(products);
//			category.setProducts(proList);

			Category save = categoryRepo.save(category);

			// deleting extra data
			// productsRepo.deleteByForeingKey(save.getC_Id());

			return save;
		}

	}

	public Products addNewProduct(Products products) {
		Category bycatagoryName = categoryRepo.findBycatagoryName(products.getCategoryName());
		String proname = products.getProductName().toLowerCase();

		products.setProductName(proname);
		products.setCategory(bycatagoryName);
		products.setDiscountedPrice(
				products.getProductPrice() - ((products.getDiscount() / 100) * 100 ));

		// Saved item
		Products save = productsRepo.save(products);
		return save;
	}

	public Category categoryUpdateService(Category category, String coverimagepath, MultipartFile file) {
		// fetching old category
		Category cId = categoryRepo.findById(category.getC_Id());
		cId.setCatagoryName(category.getCatagoryName());
		if (file.getSize() != 0) {
			// removing existing image
			fileService.removeFile(cId, deleteCoverPath);
			try {
				// saving new image
				fileService.getAndSetCategoryImage(coverimagepath, cId, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Category save = categoryRepo.save(cId);
		return save;
	}

	public List<Category> getCategoryList() {
		List<Category> list = categoryRepo.findAll();
		List<Category> listToSend = new ArrayList<Category>();
		for (Category category : list) {
			Category cate = new Category();
			cate.setC_Id(category.getC_Id());
			cate.setCatagoryName(category.getCatagoryName());
			cate.setCover(category.getCover());
			cate.setProducts(null);
			listToSend.add(cate);
		}
		return listToSend;
	}

	public List<Products> getSelectedProducts(long id) {

		Category byId = categoryRepo.findById(id);
		List<Products> products = byId.getProducts();
		List<Products> listToSend = new ArrayList<Products>();

		for (Products product : products) {
			product.setCategory(null);
			product.setCart(null);
			listToSend.add(product);
		}
		return listToSend;
	}

	public List<Products> saveToCart(long id) {
		UserBag cart = new UserBag();
		Products prod = productsRepo.findByproId(id);
		UserDAO loggedInUser = loginUserService.getLoggedInUser();

		cart.setProductsInCart(prod);
		cart.setUserDAO(loggedInUser);

		bagRepo.save(cart);
		
		List<Products> bag = new ArrayList<Products>();
		List<UserBag> existedCart = loggedInUser.getCart();
//		// getting all card items
		for (UserBag b : existedCart) {
			Products p = b.getProductsInCart();
			p.setCart(null);
			p.setCategory(null);
			bag.add(p);
		}

		return bag;
	}

	public List<Products> getCart() {
		UserDAO loggedInUser = loginUserService.getLoggedInUser();
		Address address = loggedInUser.getAddress();
		address.setAdd_user(null);

		List<Products> bag = new ArrayList<Products>();
		List<UserBag> existedCart = loggedInUser.getCart();
		Products p;

		// Getting all cart items
		for (UserBag b : existedCart) {
			List<UserBag> bags = new ArrayList<>(); // New list created each iteration
			p = b.getProductsInCart();
//			p.setCategory(null);
			
			
//			Products pro = new Products();
//			pro.setCategory(null);
//			pro.setDiscount(p.getDiscount());
//			pro.setPro_Id(p.getPro_Id());
//			pro.setProductName(p.getProductName());
//			pro.setCategoryName(p.getCategoryName());
//			pro.setSold(p.getSold());
//			pro.setDiscountedPrice(p.getDiscountedPrice());
//			pro.setProductImage(p.getProductImage());
//			pro.setProductPrice(p.getProductPrice());

			b.setUserDAO(null);
			b.setProductsInCart(null);

			// System.out.println("Processing UserBag: " + b);
			bags.add(b);

//			pro.setCart(bags);
			p.setCart(null);
			p.setCategory(null);
			p.setCart(bags); // Assign the list to the product's cart

			bag.add(p);
		}
		return bag;

	}

	public Category getCategoryForManage(long id) {
		Category cat = categoryRepo.findById(id);
		Category catToSend = new Category();
		List<Products> list = new ArrayList<>();
		// System.out.println(cat.getProducts());
		List<Products> products = cat.getProducts();
//		System.out.println(products.size());
		for (Products p : products) {
			Products newP = new Products();
			newP.setCart(null);
			newP.setCategory(null);
			newP.setCategoryName(p.getCategoryName());
			newP.setDiscount(p.getDiscount());
			newP.setPro_Id(p.getPro_Id());
			newP.setProductImage(p.getProductImage());
			newP.setProductName(p.getProductName());
			newP.setProductPrice(p.getProductPrice());
			newP.setSold(p.getSold());

			list.add(newP);

		}

		catToSend.setC_Id(cat.getC_Id());
		catToSend.setCatagoryName(cat.getCatagoryName());
		catToSend.setCover(cat.getCover());
		catToSend.setProducts(list);

		return catToSend;
	}

	public void updateProduct(@Valid Products products, MultipartFile file, String productImagePath) {
		Products prod = productsRepo.findByproId(products.getPro_Id());

		prod.setProductName(products.getProductName());
		prod.setDiscount(products.getDiscount());
		prod.setProductPrice(products.getProductPrice());
		prod.setSold(products.getSold());
		double price = prod.getProductPrice() - ((prod.getProductPrice()/100)*products.getDiscount());
		prod.setDiscountedPrice(price);
		if (file.getSize() != 0) {
			fileService.deleteProductImage(prod, deleteProductPath);
			fileService.getAndSetProductImage(productImagePath, prod, file);
		}
		productsRepo.save(prod);
		// +

	}

	public List<Products> deleteProduct(Products products) {

		Products productToDelete = productsRepo.findByproId(products.getPro_Id());

		long c_Id = productToDelete.getCategory().getC_Id();
		productsRepo.deleteById(productToDelete.getPro_Id());
		// remove image also
		fileService.deleteProductImage(productToDelete, deleteProductPath);

		List<Products> selectedProducts = this.getSelectedProducts(c_Id);
		return selectedProducts;
	}

}
