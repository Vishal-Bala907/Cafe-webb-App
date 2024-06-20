package com.cafe.entities;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Products {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pro_Id;

	@Length(min = 3, max = 20, message = "product name must be 3 to 20 characters in length")
	private String productName;
	/*
	 * @Length(min = 10, message = "product price must be atlest or more than 10rs")
	 */
	private double productPrice;
	private double discount;
	private long sold;
	private String productImage;
	private String categoryName;

	@OneToMany(mappedBy = "productsInCart")
	private List<UserBag> cart;

	@ManyToOne
	private Category category;

	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Products(
			@Length(min = 3, max = 20, message = "product name must be 3 to 20 characters in length") String productName,
			double productPrice, double discount, long sold, String productImage, String categoryName, List<UserBag> cart,
			Category category) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.discount = discount;
		this.sold = sold;
		this.productImage = productImage;
		this.categoryName = categoryName;
		this.cart = cart;
		this.category = category;
	}

	public List<UserBag> getCart() {
		return cart;
	}

	public void setCart(List<UserBag> cart) {
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "Products [pro_Id=" + pro_Id + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", discount=" + discount + ", sold=" + sold + ", productImage=" + productImage + ", categoryName="
				+ categoryName + ", cart=" + cart + ", category=" + category + "]";
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public long getPro_Id() {
		return pro_Id;
	}

	public void setPro_Id(long pro_Id) {
		this.pro_Id = pro_Id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public long getSold() {
		return sold;
	}

	public void setSold(long sold) {
		this.sold = sold;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

}
