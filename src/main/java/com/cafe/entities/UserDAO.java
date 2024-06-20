package com.cafe.entities;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class UserDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long u_id;

	@Column(name = "ROLE")
	private String ROLE;

	@Length(min = 5, max = 20, message = "Username must be 5 to 20 characters in length")
	private String username;

	@Length(min = 5, message = "Password must be 5 to 20 characters in length")
	private String password;

	private long salary;
	private String post;
	private String user_image;
	private String DOJ;
	private String DOL;

	@OneToMany(mappedBy = "userDAO")
	private List<UserBag> cart;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "address_id", referencedColumnName = "add_id")
	private Address address;

	@OneToMany(mappedBy = "user_details", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Orders> orders;

	@OneToMany(mappedBy = "att_user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Attendence> attendence;

	// Constructors, getters, and setters
	public UserDAO() {
	}

	
	@OneToMany(mappedBy = "userDAO")
	public List<UserBag> getCart() {
		return cart;
	}



	public void setCart(List<UserBag> cart) {
		this.cart = cart;
	}



	public long getU_id() {
		return u_id;
	}

	public void setU_id(long u_id) {
		this.u_id = u_id;
	}

	public String getROLE() {
		return ROLE;
	}

	public void setROLE(String rOLE) {
		ROLE = rOLE;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getUser_image() {
		return user_image;
	}

	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}

	public String getDOJ() {
		return DOJ;
	}

	public void setDOJ(String dOJ) {
		DOJ = dOJ;
	}

	public String getDOL() {
		return DOL;
	}

	public void setDOL(String dOL) {
		DOL = dOL;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	public List<Attendence> getAttendence() {
		return attendence;
	}

	public void setAttendence(List<Attendence> attendence) {
		this.attendence = attendence;
	}

	@Override
	public String toString() {
		return "UserDAO [u_id=" + u_id + ", ROLE=" + ROLE + ", username=" + username + ", password=" + password
				+ ", salary=" + salary + ", post=" + post + ", user_image=" + user_image + ", DOJ=" + DOJ + ", DOL="
				+ DOL + ", cart=" + cart + ", address=" + address + ", orders=" + orders + ", attendence=" + attendence
				+ "]";
	}

	public UserDAO(String rOLE,
			@Length(min = 5, max = 20, message = "Username must be 5 to 20 characters in length") String username,
			@Length(min = 5, message = "Password must be 5 to 20 characters in length") String password, long salary,
			String post, String user_image, String dOJ, String dOL, List<UserBag> cart, Address address,
			List<Orders> orders, List<Attendence> attendence) {
		super();
		ROLE = rOLE;
		this.username = username;
		this.password = password;
		this.salary = salary;
		this.post = post;
		this.user_image = user_image;
		DOJ = dOJ;
		DOL = dOL;
		this.cart = cart;
		this.address = address;
		this.orders = orders;
		this.attendence = attendence;
	}

	

}
