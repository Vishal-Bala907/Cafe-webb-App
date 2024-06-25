package com.cafe.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create-order/{amount}")
    public String createOrder(@PathVariable("amount") double amount) {
    	System.out.println("payment" + amount);
        try {
            return paymentService.createOrder(amount);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    	
    }
}