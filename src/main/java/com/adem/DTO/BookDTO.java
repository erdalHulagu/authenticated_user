package com.adem.DTO;


import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

	
	private Long id;
	
	private String nameOfBook; 
	
	private String nameOfBookWriter;
	
	private String contentOfBook;
	
	private Set<String> imageOfBook;
	
}
