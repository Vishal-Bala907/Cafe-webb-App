package com.cafe.entities;

import jakarta.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long add_id;

    private String country;
    private String state;
    private String city;
    private String street;
    private String house_no;
    private int postal_code;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserDAO add_user;

    // Constructors, getters, and setters
    public Address() {}

	public Address(String country, String state, String city, String street, String house_no, int postal_code,
			UserDAO add_user) {
		super();
		this.country = country;
		this.state = state;
		this.city = city;
		this.street = street;
		this.house_no = house_no;
		this.postal_code = postal_code;
		this.add_user = add_user;
	}

	public long getAdd_id() {
		return add_id;
	}

	public void setAdd_id(long add_id) {
		this.add_id = add_id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse_no() {
		return house_no;
	}

	public void setHouse_no(String house_no) {
		this.house_no = house_no;
	}

	public int getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(int postal_code) {
		this.postal_code = postal_code;
	}

	public UserDAO getAdd_user() {
		return add_user;
	}

	public void setAdd_user(UserDAO add_user) {
		this.add_user = add_user;
	}

	@Override
	public String toString() {
		return "Address [add_id=" + add_id + ", country=" + country + ", state=" + state + ", city=" + city
				+ ", street=" + street + ", house_no=" + house_no + ", postal_code=" + postal_code + ", add_user="
				+ add_user + "]";
	}

    // Add other constructors, getters, and setters as needed
    
}
