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
    private double o_p_price;
    private double discountedPrice;
   	private double o_p_discount;
    private String time;
    
    private boolean dispatched;
    private boolean cancled;
    

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "u_id")
    private UserDAO user_details;

    // Constructors, getters, and setters
    public Orders() {}

	

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



	public double getO_p_price() {
		return o_p_price;
	}



	public void setO_p_price(double o_p_price) {
		this.o_p_price = o_p_price;
	}



	public double getDiscountedPrice() {
		return discountedPrice;
	}



	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}



	public double getO_p_discount() {
		return o_p_discount;
	}



	public void setO_p_discount(double o_p_discount) {
		this.o_p_discount = o_p_discount;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public boolean isDispatched() {
		return dispatched;
	}



	public void setDispatched(boolean dispatched) {
		this.dispatched = dispatched;
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
				+ ", o_p_price=" + o_p_price + ", discountedPrice=" + discountedPrice + ", o_p_discount=" + o_p_discount
				+ ", time=" + time + ", dispatched=" + dispatched + "]";
	}



	public boolean isCancled() {
		return cancled;
	}



	public void setCancled(boolean cancled) {
		this.cancled = cancled;
	}

	
}
