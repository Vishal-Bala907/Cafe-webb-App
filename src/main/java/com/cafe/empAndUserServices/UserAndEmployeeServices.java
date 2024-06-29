package com.cafe.empAndUserServices;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.entities.Address;
import com.cafe.entities.Orders;
import com.cafe.entities.Products;
import com.cafe.entities.UserDAO;
import com.cafe.loginService.LoginUserService;
import com.cafe.repos.AddressRepo;
import com.cafe.repos.BagRepo;
import com.cafe.repos.OrdersRepo;
import com.cafe.repos.UserRepo;

import jakarta.validation.Valid;

@Service
public class UserAndEmployeeServices {
	@Autowired
	UserRepo userRepo;
	@Autowired
	BagRepo bagRepo;
	@Autowired 
	LoginUserService loginUserService;
	@Autowired
	AddressRepo addressRepo;
	@Autowired
	OrdersRepo ordersRepo;

	public UserDAO getUserByUserName(String username) {

		UserDAO byUsername = userRepo.findByUsername(username);
		if (byUsername == null) {
			return null;
		}

		byUsername.setCart(null);
		byUsername.setAddress(null);
		byUsername.setAttendence(null);
		byUsername.setOrders(null);

		return byUsername;
	}

	public UserDAO addEmployee(@Valid UserDAO dao) {

		UserDAO byUsername = userRepo.findById(dao.getU_id());
		byUsername.setUsername(dao.getUsername());
		byUsername.setSalary(dao.getSalary());
		byUsername.setPost(dao.getPost());
		byUsername.setROLE(dao.getROLE());

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate doj = LocalDate.parse(dao.getDOJ(), format);
		LocalDate dol = LocalDate.parse(dao.getDOL(), format);

		byUsername.setDOJ(doj.toString());
		byUsername.setDOL(dol.toString());

		UserDAO save = userRepo.save(byUsername);

		return save;
	}

	public List<UserDAO> getMyUsers(String sort) {

		System.out.println(sort);

		List<UserDAO> users;
		if (sort.equals("ALL")) {
			users = userRepo.findAll();
		} else {
			users = userRepo.findByROLE(sort);
		}

		// making ready the list to send to frontend
		List<UserDAO> listToSend = new ArrayList<UserDAO>();
		for (UserDAO userDAO : users) {

			userDAO.setCart(null);
			
			userDAO.setOrders(null);
			
			userDAO.setAttendence(null);
			
			userDAO.getAddress().setAdd_user(null);

			Address address = new Address();
			address = userDAO.getAddress();
			address.setAdd_user(null);
			
			listToSend.add(userDAO);
		}
		return listToSend;

	}

	public void removeFromCart(long id) {
		bagRepo.deleteById(id);
	}

	public Address getAddress() {
		UserDAO loggedInUser = loginUserService.getLoggedInUser();
		Address address = loggedInUser.getAddress();
		UserDAO add_user = address.getAdd_user();
		add_user.setCart(null);
		add_user.setAttendence(null);
		add_user.setOrders(null);
		add_user.setAddress(null);
		
		address.setAdd_user(add_user);
		
		return address;
		
	}

	public Address updateAddress(Address address) {
		//long userId = address.getAdd_user().getU_id();
		Address updatedAddress = addressRepo.findById(address.getAdd_id());
		updatedAddress.setCity(address.getCity());
		updatedAddress.setCountry(address.getCountry());
		updatedAddress.setHouse_no(address.getHouse_no());
		updatedAddress.setPostal_code(address.getPostal_code());
		updatedAddress.setState(address.getState());
		updatedAddress.setStreet(address.getStreet());
		
		Address save = addressRepo.save(updatedAddress);
		return save;
	}

	public void createOrders(List<Products> orders) {
		
		for(Products p : orders) {
			// saving Order datails in the database
			Orders create = new Orders();
			create.setO_p_discount((float) p.getDiscount());
			create.setO_p_id(p.getPro_Id());
			create.setO_p_name(p.getProductName());
			create.setO_p_price(p.getProductPrice());
			create.setDiscountedPrice(p.getDiscountedPrice());
			create.setOrder_date(LocalDate.now().toString());
			create.setTime(LocalTime.now().toString());
			create.setUser_details(loginUserService.getLoggedInUser());
			create.setDispatched(false);
			ordersRepo.save(create);
			
			// now delete from bag
			bagRepo.deleteById(p.getCart().get(0).getCartId());
			
		}
		
	}
	
	public List<Orders> getAllUnDispatchedOrders(){
		List<Orders> unDispatchedOrders = ordersRepo.findUnDispatchedOrders();
		List<Orders> listTosend = new ArrayList<>();
		for(Orders o : unDispatchedOrders) {
			
			UserDAO user = o.getUser_details();
			user.setCart(null);
			user.setAttendence(null);
			user.setOrders(null);
			
			Address address =	user.getAddress();
			address.setAdd_user(null);
			user.setAddress(address);
			
			o.setUser_details(user);
			listTosend.add(o);
		}
		return listTosend;
	}

}
