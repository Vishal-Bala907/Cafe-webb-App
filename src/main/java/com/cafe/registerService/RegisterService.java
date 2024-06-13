package com.cafe.registerService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.entities.Address;
import com.cafe.entities.Attendence;
import com.cafe.entities.Orders;
import com.cafe.entities.UserDAO;
import com.cafe.fileService.FileService;
import com.cafe.passEncoDeco.PassEncoDeco;
import com.cafe.repos.UserRepo;

@Service
public class RegisterService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	PassEncoDeco encoDeco;
	@Autowired
	FileService fileservice;

	@Transactional
	public UserDAO saveUser(UserDAO user, String path, MultipartFile file) throws Exception {
		UserDAO byUsername = userRepo.findByUsername(user.getUsername());

		if (byUsername != null) {
			throw new Exception("User already exists for this name");
		} else {
			UserDAO newuser = fileservice.setAndUploadFile(path, user, file);
			// password , id , username ,image
			newuser.setROLE("ROLE_USER");
			newuser.setDOJ("123");
			newuser.setDOL("1234");
			newuser.setSalary(12000);
			newuser.setPost("customer");
			newuser.setPassword(encoDeco.encode(newuser.getPassword()));

			Address address = new Address();
			address.setAdd_user(newuser);
			address.setCity("Kota");
			address.setCountry("india");
			address.setHouse_no("46-b");
			address.setPostal_code(324009);
			address.setState("Rajasthan");
			address.setStreet("Modi college road");

			newuser.setAddress(address);

//			****************
			Orders orders = new Orders();
			orders.setO_p_discount(10);
			orders.setO_p_id(0);
			orders.setO_p_name("cake");
			orders.setO_p_price(200);
			orders.setOrder_date("2354");
			orders.setQuantity(34);
			orders.setUser_details(newuser);

//			**************
			Attendence attendence = new Attendence();
			attendence.setAtt_user(newuser);
			attendence.setDate("ertr");
			attendence.setEmp_username("vishal");
			attendence.setIn_time("45");
			attendence.setOut_time("546");

			List<Orders> ordersList = new ArrayList<Orders>();
			List<Attendence> attendences = new ArrayList<Attendence>();
			ordersList.add(orders);
			attendences.add(attendence);

			newuser.setOrders(ordersList);
			newuser.setAttendence(attendences);

//			try {
//				UserDAO savedUser = userRepo.save(newuser);
//				System.out.println("User saved successfully");
				return newuser;
//			} catch (Exception e) {
//				System.out.println("Error saving user: " + e.getMessage());
//				e.printStackTrace();
//				throw e;
//			}

		
		}

	}
}
