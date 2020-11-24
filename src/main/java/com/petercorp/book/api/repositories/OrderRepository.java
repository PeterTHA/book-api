package com.petercorp.book.api.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.petercorp.book.api.entity.OrderEntity;
import com.petercorp.book.api.entity.UserEntity;
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer>{
	@Transactional
	@Query ("select i.orderId from OrderEntity i where i.user_name =:userName")
	List findByUsername(String userName);

	@Transactional
	@Modifying
	@Query ("delete from OrderEntity i where i.user_name =:userName")
	void deleteOrderByUserName(String userName);
}
