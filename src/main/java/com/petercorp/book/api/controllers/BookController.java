package com.petercorp.book.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petercorp.book.api.entity.BookEntity;
import com.petercorp.book.api.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;

	@GetMapping
	public ResponseEntity<List<BookEntity>> getAllBook() {
	    return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBook());
	}
}
