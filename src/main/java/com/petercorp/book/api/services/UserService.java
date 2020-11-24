package com.petercorp.book.api.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petercorp.book.api.entity.UserEntity;
import com.petercorp.book.api.model.BookBodyResponse;
import com.petercorp.book.api.model.UserBodyRequest;
import com.petercorp.book.api.model.UserBodyResponse;
import com.petercorp.book.api.repositories.OrderDetailRepository;
import com.petercorp.book.api.repositories.OrderRepository;
import com.petercorp.book.api.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Transactional
	public UserBodyResponse getUserByUserName(String userName) {
	
		UserEntity user = new UserEntity();
		user = userRepository.findByUsername(userName);
		List<Integer> orderList = orderRepository.findByUsername(userName);
		System.out.println("order list" + orderList);
		List<BookBodyResponse> booList = orderDetailRepository.findByOrderId(orderList);
		System.out.println("book list" + booList);
		return UserBodyResponse.builder().name(user.getName()).surname(user.getSurename()).date_of_birth(user.getDob()).books(booList).build();
		
		
		
	}
	@Transactional
	public String adduser(UserBodyRequest userBodyRequest) {
		String encodedPassword = bcryptEncoder.encode(userBodyRequest.getPassword());
		String result = "";
		System.out.println("userBodyRequest :: "+userBodyRequest);
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(userBodyRequest.getUsername());
		userEntity.setPassword(encodedPassword);
		userEntity.setDob(userBodyRequest.getDate_of_birth());
		userEntity.setName(userBodyRequest.getName());
		userEntity.setSurename(userBodyRequest.getSurname());
		if (userRepository.coutnUserName(userBodyRequest.getUsername()) == 0) {
			userRepository.save(userEntity);
		} else {
			result = "user name is already exitst";
		}
		return result;
	}
	@Transactional
	public void deleteUserByUserName(String userName) {
		//delete user
		userRepository.deleteUserByUserName(userName);
		//delete order
		orderRepository.deleteOrderByUserName(userName);
	}

}
