package com.cafe.entities;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long c_Id;

	@Length(min = 3, max = 20, message = "catagory name must be 2 to 20 characters in length")
	private String catagoryName;
	private String cover;

	@OneToMany(mappedBy = "category")
	private List<Products> products;

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(
			@Length(min = 3, max = 20, message = "catagory name must be 2 to 20 characters in length") String catagoryName,
			String cover, List<Products> products) {
		super();
		this.catagoryName = catagoryName;
		this.cover = cover;
		this.products = products;
	}

	public long getC_Id() {
		return c_Id;
	}

	public void setC_Id(long c_Id) {
		this.c_Id = c_Id;
	}

	public String getCatagoryName() {
		return catagoryName;
	}

	public void setCatagoryName(String catagoryName) {
		this.catagoryName = catagoryName;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Category [c_Id=" + c_Id + ", catagoryName=" + catagoryName + ", cover=" + cover + ", products="
				+ products + "]";
	}

}
