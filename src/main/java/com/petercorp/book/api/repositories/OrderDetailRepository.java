package com.petercorp.book.api.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.petercorp.book.api.entity.OrderDetailEntity;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer>{

	@Transactional
	@Query ("select distinct i.bookId from OrderDetailEntity i where i.orderEntity.orderId IN :orderId")
	List findByOrderId(List<Integer> orderId);
}
