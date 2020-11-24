package com.petercorp.book.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petercorp.book.api.config.JwtTokenUtil;
import com.petercorp.book.api.model.UserBodyRequest;
import com.petercorp.book.api.model.UserBodyResponse;
import com.petercorp.book.api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@GetMapping
	public ResponseEntity<UserBodyResponse> getUserById(@RequestHeader("Authorization") String token) {
		System.out.println("token :: "+token);
		token = token.substring(7);
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		System.out.println("userName :: "+userName);
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserName(userName));
	}

	@PostMapping
	public String addUser(@RequestBody UserBodyRequest user) {
		String result = userService.adduser(user);
		return result;
	}

	@DeleteMapping
	public HttpStatus deleteUser(@RequestHeader("Authorization") String token) {
		System.out.println("token :: "+token);
		token = token.substring(7);
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		System.out.println("userName :: "+userName);
		userService.deleteUserByUserName(userName);;
		return HttpStatus.OK;
	}

}
