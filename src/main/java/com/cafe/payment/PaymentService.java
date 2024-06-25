package com.cafe.payment;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class PaymentService {
	@Autowired
	RazorpayConfig config;
	public String createOrder(double amount)throws Exception {
		RazorpayClient client = new RazorpayClient(config.getKey(), config.getSecret());
		JSONObject orderRequest = new JSONObject();
		
		orderRequest.put("amount", amount * 100); // amount in the smallest currency unit
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_123456");
        orderRequest.put("payment_capture", 1); // auto capture
        
        Order order = client.orders.create(orderRequest);
        return order.toString();
	}
}
