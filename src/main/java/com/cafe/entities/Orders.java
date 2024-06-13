package com.cafe.entities;

import jakarta.persistence.*;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long o_id;

    private String order_date;
    private String o_p_name;
    private long o_p_id;
    private int o_p_price;
    private float o_p_discount;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "u_id")
    private UserDAO user_details;

    // Constructors, getters, and setters
    public Orders() {}

	public Orders(String order_date, String o_p_name, long o_p_id, int o_p_price, float o_p_discount, int quantity,
			UserDAO user_details) {
		super();
		this.order_date = order_date;
		this.o_p_name = o_p_name;
		this.o_p_id = o_p_id;
		this.o_p_price = o_p_price;
		this.o_p_discount = o_p_discount;
		this.quantity = quantity;
		this.user_details = user_details;
	}

	public long getO_id() {
		return o_id;
	}

	public void setO_id(long o_id) {
		this.o_id = o_id;
	}

	public String getOrder_date() {
		return order_date;
	}

	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}

	public String getO_p_name() {
		return o_p_name;
	}

	public void setO_p_name(String o_p_name) {
		this.o_p_name = o_p_name;
	}

	public long getO_p_id() {
		return o_p_id;
	}

	public void setO_p_id(long o_p_id) {
		this.o_p_id = o_p_id;
	}

	public int getO_p_price() {
		return o_p_price;
	}

	public void setO_p_price(int o_p_price) {
		this.o_p_price = o_p_price;
	}

	public float getO_p_discount() {
		return o_p_discount;
	}

	public void setO_p_discount(float o_p_discount) {
		this.o_p_discount = o_p_discount;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public UserDAO getUser_details() {
		return user_details;
	}

	public void setUser_details(UserDAO user_details) {
		this.user_details = user_details;
	}

	@Override
	public String toString() {
		return "Orders [o_id=" + o_id + ", order_date=" + order_date + ", o_p_name=" + o_p_name + ", o_p_id=" + o_p_id
				+ ", o_p_price=" + o_p_price + ", o_p_discount=" + o_p_discount + ", quantity=" + quantity
				+ ", user_details=" + user_details + "]";
	}

    // Add other constructors, getters, and setters as needed
    
}
