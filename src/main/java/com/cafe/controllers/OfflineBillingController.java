package com.cafe.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.entities.Orders;
import com.cafe.entities.Products;
import com.cafe.repos.OrdersRepo;
import com.cafe.repos.ProductsRepo;

@Service
public class OfflineBillingController {
	@Autowired
	ProductsRepo productsRepo;
	@Autowired
	OrdersRepo ordersRepo;

	public Map<String, String> createOfflineOrder(List<Products> list) {
		for (Products product : list) {
			// fetching the product object
			Products byproId = productsRepo.findByproId(product.getPro_Id());
			// getting the old sold number
			long oldCount = byproId.getSold();
			// setting the new sold quantity
			byproId.setSold(byproId.getSold() + oldCount);

			// creating new Order object
//			System.out.println(oldCount +" --- "+ product.getSold());
			for (long i = oldCount; i < product.getSold(); i++) {
				Orders orders = new Orders();
				orders.setCancled(false);
				orders.setDiscountedPrice(byproId.getDiscountedPrice());
				orders.setDispatched(true);
				orders.setO_p_discount(byproId.getDiscount());
				orders.setO_p_id(byproId.getPro_Id());
				orders.setO_p_name(byproId.getProductName());
				orders.setO_p_price(byproId.getProductPrice());
				orders.setOrder_date(LocalDate.now().toString());
				orders.setTime(LocalTime.now().toString());

				LocalDate date = LocalDate.now();
				LocalDateTime dateTime = date.atStartOfDay();
				long timestamp = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();

				orders.setTimestamp(timestamp);
				
				ordersRepo.save(orders);
				
			}
			productsRepo.save(byproId);

		}
		
		Map<String, String> map = new HashMap<>();
		map.put("status", "done");
		return map;
	}

	public List<Products> getSearchedItems(String name) {
		List<Products> byName = productsRepo.findByName(name);
		return byName;
	}

}
