package com.petercorp.book.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.petercorp.book.api.config.JwtTokenUtil;
import com.petercorp.book.api.model.OrderBodyRequest;
import com.petercorp.book.api.model.OrderBodyResponse;
import com.petercorp.book.api.services.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@RequestMapping(value = "/users/orders", method = RequestMethod.POST)
	public ResponseEntity<OrderBodyResponse> saveUser(@RequestHeader("Authorization") String token, @RequestBody OrderBodyRequest order) {
		System.out.println("token :: "+token);
		token = token.substring(7);
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		System.out.println("userName :: "+userName);
		OrderBodyResponse orderResponse = new OrderBodyResponse(); 
		HttpStatus HttpStatus = null;
		try {
			orderResponse = orderService.addOrder(order, userName);
			HttpStatus = HttpStatus.OK;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			HttpStatus = HttpStatus.BAD_REQUEST;
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus).body(orderResponse);
	}
}
