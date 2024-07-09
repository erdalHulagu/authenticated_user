package com.adem.DTO;

import java.util.HashSet;
import java.util.Set;

import com.adem.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

private String firstName;

private String lastName;

private String email;

private String password;

private String phone;

private String address;

private boolean builtIn;

private  Set<String> roles ;

public void setRoles(Set<Role> roles) {
	
	Set<String> roleStr = new HashSet<>();
	
	roles.forEach( r-> {
		roleStr.add(r.getType().getName()); // Administrator veya Customer gözükecek
		
	}); 
	
	this.roles = roleStr;
}


	
}



