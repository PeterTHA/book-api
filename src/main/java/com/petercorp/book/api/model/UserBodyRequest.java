package com.petercorp.book.api.model;

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
public class UserBodyRequest {
	
	private String username;
	private String password; 
	private String date_of_birth;
	private String name;
	private String surname;
	
}
