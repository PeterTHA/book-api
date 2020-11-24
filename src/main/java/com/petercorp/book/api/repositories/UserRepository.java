package com.petercorp.book.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.petercorp.book.api.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	@Transactional
	@Query ("select count(i) from UserEntity i where i.username =:userName")
    long coutnUserName(String userName);
	@Transactional
	@Query ("select i from UserEntity i where i.username =:userName")
	UserEntity findByUsername(String userName);
	@Transactional
	@Modifying
	@Query ("delete from UserEntity i where i.username =:userName")
	void deleteUserByUserName(String userName);
}
