package com.tunehub.demo.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.tunehub.demo.entities.Users;
import com.tunehub.demo.service.UsersService;

import jakarta.servlet.http.HttpSession;

@Controller
public class paymentController {

	@Autowired
	UsersService service;

	@GetMapping("/pay")
	public String pay() {
		return "pay";
	}

	@SuppressWarnings("finally")
	@PostMapping("/createOrder")
	@ResponseBody
	public String createOrder(HttpSession session) {

		int amount = 5000;
		Order order = null;

		try {
			RazorpayClient razorpay = new RazorpayClient("rzp_test_e5VUZxbxQBEvY1", "mFIKS9Byz3K8cFJHqbu5Igir");

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", amount * 100);
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "receipt#1");

			order = razorpay.orders.create(orderRequest);

			String mail = (String) session.getAttribute("email");
			Users u = service.getUser(mail);
			u.setPremium(true);
			service.updateUser(u);
		} catch (RazorpayException e) {
			e.printStackTrace();
		} finally {
			return order.toString();
		}
	}

	@PostMapping("/verify")
	@ResponseBody
	public boolean verifyPayment(@RequestParam String orderId, @RequestParam String paymentId,
			@RequestParam String signature) {
		try {
			// Initialize Razorpay client with your API key and secret
			@SuppressWarnings("unused")
			RazorpayClient razorpayClient = new RazorpayClient("rzp_test_e5VUZxbxQBEvY1", "mFIKS9Byz3K8cFJHqbu5Igir");
			// Create a signature verification data string
			String verificationData = orderId + "|" + paymentId;

			// Use Razorpay's utility function to verify the signature
			boolean isValidSignature = Utils.verifySignature(verificationData, signature, "0BqRP7065kuUDvV6ZaimqRSa");

			return isValidSignature;
		} catch (RazorpayException e) {
			e.printStackTrace();
			return false;
		}
	}

//payment-success -> update to premium user
	@GetMapping("payment-success")
	public String paymentSuccess(HttpSession session) {

		String email = (String) session.getAttribute("email");
		Users user = service.getUser(email);
		user.setPremium(true);
		service.updateUser(user);

		return "login";
	}

//payment-failure -> redirect to login 
	@GetMapping("payment-failure")
	public String paymentFailure() {
		// payment-error page
		return "login";
	}

}
