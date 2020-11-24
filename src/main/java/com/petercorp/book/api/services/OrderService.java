package com.petercorp.book.api.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petercorp.book.api.entity.BookEntity;
import com.petercorp.book.api.entity.OrderDetailEntity;
import com.petercorp.book.api.entity.OrderEntity;
import com.petercorp.book.api.model.BookBodyResponse;
import com.petercorp.book.api.model.OrderBodyRequest;
import com.petercorp.book.api.model.OrderBodyResponse;
import com.petercorp.book.api.repositories.OrderDetailRepository;
import com.petercorp.book.api.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private BookService bookService;

	public Optional<OrderEntity> getOrder(int id) {
		return orderRepository.findById(id);
	}

	@Transactional
	public OrderBodyResponse addOrder(OrderBodyRequest order, String userName) {

		List<OrderDetailEntity> orderDetailList = new ArrayList<OrderDetailEntity>();
		ArrayList<Integer> orderList = new ArrayList<Integer>(order.getOrders());
		OrderEntity orderEntity = OrderEntity.builder().user_name(userName).build();
		Double total_amt = 0.00;
		for (int i = 0; i < orderList.size(); i++) {
			OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
			orderDetailEntity.setOrderEntity(orderEntity);
			orderDetailEntity.setBookId(orderList.get(i));
			Optional<BookEntity> book = Optional.of(new BookEntity());
			book = bookService.getBookById(orderList.get(i));
			total_amt +=book.get().getPrice();
			orderDetailEntity.setPrice(book.get().getPrice());
			orderDetailList.add(orderDetailEntity);
		}
		orderEntity.setTotal_amt(total_amt);
		System.out.println(" orderDetailList " + orderDetailList);
		System.out.println(" orderEntity " + orderEntity);
		orderDetailRepository.saveAll(orderDetailList);
		return OrderBodyResponse.builder().price(orderEntity.getTotal_amt()).build();
	}

	public void deleteOrder(int id) {
		orderRepository.deleteById(id);
	}

}
