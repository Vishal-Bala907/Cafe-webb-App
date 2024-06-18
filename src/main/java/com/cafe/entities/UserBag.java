package com.cafe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserBag {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long cartId;

	@ManyToOne
	@JoinColumn(unique = false)
	private Products productsInCart;

	@ManyToOne
	@JoinColumn(unique = false)
	private UserDAO userDAO;

	public UserBag() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserBag(Products productsInCart, UserDAO userDAO) {
		super();
		this.productsInCart = productsInCart;
		this.userDAO = userDAO;
	}

	public long getCartId() {
		return cartId;
	}

	public void setCartId(long cartId) {
		this.cartId = cartId;
	}

	public Products getProductsInCart() {
		return productsInCart;
	}

	public void setProductsInCart(Products productsInCart) {
		this.productsInCart = productsInCart;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
