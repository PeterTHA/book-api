package com.petercorp.book.api.repositories;


import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.petercorp.book.api.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Integer>{
	
}
