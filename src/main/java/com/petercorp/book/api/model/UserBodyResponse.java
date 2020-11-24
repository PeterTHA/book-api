package com.petercorp.book.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserBodyResponse {
	
	private String name;
	private String surname; 
	private String date_of_birth;
	List<BookBodyResponse> books;
	
}
