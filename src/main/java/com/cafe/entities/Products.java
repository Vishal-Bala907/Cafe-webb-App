package com.cafe.entities;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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

	@ManyToOne
	private Category category;

	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Products(
			@Length(min = 3, max = 20, message = "product name must be 3 to 20 characters in length") String productName,
			@Length(min = 10, message = "product price must be atlest or more than 10rs") double productPrice,
			double discount, long sold, String productImage, String categoryName, Category category) {
		super();
		this.productName = productName;
		this.productPrice = productPrice;
		this.discount = discount;
		this.sold = sold;
		this.productImage = productImage;
		this.categoryName = categoryName;
		this.category = category;
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
