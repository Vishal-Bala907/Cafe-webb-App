package com.cafe.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe.entities.Address;
import com.cafe.entities.Orders;
import com.cafe.entities.Products;
import com.cafe.entities.UserDAO;
import com.cafe.services.CommonServices;
import com.cafe.services.UserAndEmployeeServices;

@RestController
@RequestMapping("/empAndCus")
public class EmpAndCustomerRestController {
	@Autowired
	UserAndEmployeeServices andEmployeeServices;
	@Autowired
	CommonServices commonServices;
	@Autowired
	OfflineBillingController billingController;

	@GetMapping("/userByName/{username}")
	public UserDAO getUserDetailsByName(@PathVariable String username, Model model) {
		UserDAO userByUserName = andEmployeeServices.getUserByUserName(username);
		if (userByUserName == null) {
			return null;
		}
		return userByUserName;
	}

	@GetMapping("/getEmpOrCus/{sort}")
	public ResponseEntity<List<UserDAO>> getEmpOrCustomers(@PathVariable String sort) {

		List<UserDAO> myUsers = andEmployeeServices.getMyUsers(sort);
		return ResponseEntity.ok().body(myUsers);
	}

	@GetMapping("/remove-from-cart/{id}")
	public ResponseEntity<List<Products>> removeFromCart(@PathVariable("id") long id) {
		andEmployeeServices.removeFromCart(id);
		List<Products> cart = commonServices.getCart();
		System.out.println(id);
		return ResponseEntity.ok().body(cart);
	}

	@GetMapping("/get-address")
	public ResponseEntity<Address> getAddress() {
		Address adress = andEmployeeServices.getAddress();
		return ResponseEntity.ok().body(adress);
	}

	@PostMapping("/create-order")
	public ResponseEntity<List<Products>> createOrder(@RequestBody List<Products> orders) {

		andEmployeeServices.createOrders(orders);
		List<Products> cart = commonServices.getCart();

		return ResponseEntity.ok().body(cart);
	}

	@GetMapping("/undispathced-orders")
	public ResponseEntity<List<Orders>> getUndispathcedOrders() {
		List<Orders> allUnDispatchedOrders = andEmployeeServices.getAllUnDispatchedOrders();
		return ResponseEntity.ok().body(allUnDispatchedOrders);
	}

	@PutMapping("/dispatch-order")
	public ResponseEntity<String> createOrder(@RequestBody Orders order) {

		String jsonObject = andEmployeeServices.dispatchOrder(order);

		return ResponseEntity.ok().body(jsonObject);
	}

	@PutMapping("/cancel-order")
	public ResponseEntity<String> cancelOrder(@RequestBody Orders order) {

		String jsonObject = andEmployeeServices.cancelOrder(order);

		return ResponseEntity.ok().body(jsonObject);
	}

	@GetMapping("/graph-data/{time}")
	public ResponseEntity<List<Map<String, Double>>> graphData(@PathVariable("time") String time) {

		List<Map<String, Double>> dataForGraph = andEmployeeServices.getDataForGraph(time);
		return ResponseEntity.ok().body(dataForGraph);
	}
	@GetMapping("/getprofile")
	public ResponseEntity<UserDAO> getProfile(){
		UserDAO profile = andEmployeeServices.profile();
		return ResponseEntity.ok().body(profile);
	}
	
	@PutMapping("/update-address")
	public ResponseEntity<Address> updateAddress(@RequestBody Address address) {
		System.out.println(address);
		Address updateAddress = andEmployeeServices.updateAddress(address);
		return ResponseEntity.ok().body(updateAddress);
	}

	@PutMapping("/create-off-bill")
	public ResponseEntity<Map<String,String>> updateBillFromOfflineOrder(@RequestBody List<Products> list){
		
		Map<String , String> order = billingController.createOfflineOrder(list);
		
		return ResponseEntity.ok().body(order);
	}
	
	@GetMapping("/off-search/{name}")
	public ResponseEntity<List<Products>> getOfflineSearchedItem(@PathVariable("name") String name){
		List<Products> list = billingController.getSearchedItems(name);
		return ResponseEntity.ok().body(list);
	}
	

}
