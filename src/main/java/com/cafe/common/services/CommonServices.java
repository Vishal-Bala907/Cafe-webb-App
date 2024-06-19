package com.cafe.common.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.entities.Category;
import com.cafe.entities.Products;
import com.cafe.entities.UserBag;
import com.cafe.entities.UserDAO;
import com.cafe.loginService.LoginUserService;
import com.cafe.repos.BagRepo;
import com.cafe.repos.CategoryRepo;
import com.cafe.repos.ProductsRepo;

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

	public Category CategorySaveService(Category category) throws Exception {

		Category bycatagoryName = categoryRepo.findBycatagoryName(category.getCatagoryName());
		if (bycatagoryName != null) {
			throw new Exception("a category with the same name is already exists");
		} else {
			Products products = new Products();
			products.setDiscount(10);
			products.setProductImage("null");
			products.setProductName("none");
			products.setProductPrice(234);
			products.setSold(0);
			products.setCategory(category);

			List<Products> proList = new ArrayList<Products>();
			proList.add(products);
			category.setProducts(proList);

			Category save = categoryRepo.save(category);

			// deleting extra data
			productsRepo.deleteByForeingKey(save.getC_Id());

			return save;
		}

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
//		
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

}
