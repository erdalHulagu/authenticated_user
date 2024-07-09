 package com.adem.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adem.DTO.UserDTO;
import com.adem.DTOresponse.Response;
import com.adem.DTOresponse.ResponseMessage;
import com.adem.domain.User;
import com.adem.request.UpdateUserRequest;
import com.adem.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
		
		UserDTO userDTO=userService. getUserById(id);
		
		return ResponseEntity.ok(userDTO);
	
	}

	
	
	
	@GetMapping("/profile")
	@PreAuthorize( "hasRole('ADMIN') or hasRole('ANONYMOUS')")
	public ResponseEntity<UserDTO> findUserProfile(){
		
	UserDTO userDTO=	userService.findUserProfile();
	 
	return ResponseEntity.ok(userDTO);

	}

	@GetMapping("/all")
	public ResponseEntity<List<UserDTO>>getAllUser(){

		List<UserDTO> usersDTO = userService.getAllUsers();

		return ResponseEntity.ok(usersDTO);
		
	}
	@PutMapping("/auth/update")
	public ResponseEntity<Response> upDateUser(@Validated @RequestBody UpdateUserRequest updateUserRequest){
		
		 userService.updateUser(updateUserRequest);
		 
		 
		 Response response = new Response();
		 response.setMessage(ResponseMessage.USER_UPDATED_MESSAGE);
		 return ResponseEntity.ok(response);
		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Response> deleteUser(@PathVariable Long id){
		
		userService.deleteUserWithId(id);
		Response response = new Response();
		 response.setMessage(ResponseMessage.USER_DELETED);
		
		return ResponseEntity.ok(response);
		
		
	
	}

	
	
}


















