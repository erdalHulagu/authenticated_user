package com.adem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adem.DTO.BookDTO;
import com.adem.DTOresponse.BookResponse;
import com.adem.DTOresponse.ResponseMessage;
import com.adem.service.BookService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
	
	
	@Autowired
	private BookService bookService;
	
	@PostMapping("/create/{imageId}")
	public ResponseEntity<BookResponse> createBook(@PathVariable String imageId, @Valid @RequestBody BookDTO bookDTO ){
		
		bookService.createBookByUser(imageId,bookDTO);
		
		BookResponse response = new BookResponse();
		
		response.setMessage(ResponseMessage.BOOK_CREATED_SUCCESFULLY);
		response.setSucces(true);
		
		return ResponseEntity.ok(response);
	}
	
	
	
	@GetMapping("/{id}")
	public ResponseEntity<BookDTO> getBook(@PathVariable Long bookId){
		
	BookDTO bookDTO	=bookService.getBookById(bookId);
	
	return ResponseEntity.ok(bookDTO);
		
	}

}
