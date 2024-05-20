package com.adem.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adem.DTO.UserDTO;
import com.adem.domain.Role;
import com.adem.domain.User;
import com.adem.enums.RoleType;
import com.adem.exception.ResourceNotFoundException;
import com.adem.exception.message.ErrorMessage;
import com.adem.mapper.UserMapper;
import com.adem.repository.UserRepository;
import com.adem.request.RegisterRequest;
import com.adem.request.UpdateUserRequest;
import com.adem.security.config.SecurityUtils;
import com.adem.security.jwt.JwtUtils;

@Service
public class UserService {

	private UserRepository userRepository;

	private UserMapper userMapper;

	private RoleService roleService;

	private PasswordEncoder passwordEncoder;

	private JwtUtils jwtUtils;
	
	private UserService userService;

	public UserService(UserRepository userRepository,UserMapper userMapper, @Lazy UserService userService,
			RoleService roleService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {

		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
		this.userService = userService;
		

	}

	// ------------------ get current user ------------------------
	public User getCurrentUser() {

		String email = SecurityUtils.getCurrentUserLogin()
				.orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.PRINCIPAL_FOUND_MESSAGE));
		User user = getUserByEmail(email);
		return user;

	}

	// ------------------ get current userDTO ------------------------
	public UserDTO getPrincipal() {
		User currentUser = getCurrentUser();
		UserDTO userDTO = userMapper.userToUserDto(currentUser);
		return userDTO;

	}

// -------------------  get user by id --------------
	public UserDTO getUserById(Long id) {

		User user = userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));

		UserDTO userDTO = userMapper.userToUserDto(user);
		return userDTO;

	}


	// --------------------get user profile------------------------
	public UserDTO findUserProfile() {

		User user = getCurrentUser();
		
		if (user == null) {
			throw new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, user));

		}
		Role role = roleService.findByType(RoleType.ROLE_ANONYMOUS);

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		userRepository.save(user);
		
        user.setCreateAt(LocalDateTime.now());
		UserDTO userDTO = userMapper.userToUserDto(user);
		return userDTO;

	}


//--------------------get all users------------------------
	public List<UserDTO> getAllUsers() {
		List<User> userList = userRepository.findAll();

		if (userList.isEmpty()) {
			new ResourceNotFoundException(String.format(ErrorMessage.USER_LIST_IS_EMPTY));
		}
		List<UserDTO> userDTOList = userMapper.userToUserDTOList(userList);
		return userDTOList;
	}

	//--------------update user-------------------
	public void updateUser(UpdateUserRequest updateUserRequest) {
		User user=userService.getCurrentUser();

		if ((user == null)) {
			new ResourceNotFoundException(String.format(ErrorMessage.EMAIL_IS_NOT_MATCH));

		}
		Role role = roleService.findByType(RoleType.ROLE_ANONYMOUS);

		Set<Role> roles = new HashSet<>();
		
		String encodedPassword = passwordEncoder.encode(updateUserRequest.getPassword());
		
		roles.add(role);
		user.setRoles(roles);
		user.setUpdateAt(LocalDateTime.now());
		user.setAddress(updateUserRequest.getAddress());
		user.setFirstName(updateUserRequest.getFirstName());
		user.setLastName(updateUserRequest.getLastName());
		user.setPhone(updateUserRequest.getPhone());
		user.setPassword(encodedPassword);
		userRepository.save(user);

	}

	// ---------------- register user----------------------

	public void saveUser(RegisterRequest registerRequest) {
		

		Role role = roleService.findByType(RoleType.ROLE_ANONYMOUS);

		Set<Role> roles = new HashSet<>();
		roles.add(role);

		String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

		User user = new User();;
		user.setRoles(roles);
		user.setPassword(encodedPassword);
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setEmail(registerRequest.getEmail());
		user.setAddress(registerRequest.getAddress());
		user.setPhone(registerRequest.getPhone());
		user.setCreateAt(LocalDateTime.now());

		userRepository.save(user);

	}



	public void deleteUserWithId(Long id) {

		userRepository.deleteById(id);

	}

//------------- find user by email-------------------
	public User getUserByEmail(String email) {

		User user = userRepository.findByEmail(email).orElseThrow(
				() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email)));
		return user;
	}

//------------ convert roles------------------
	public Set<Role> convertRoles(Set<String> pRoles) {
		Set<Role> roles = new HashSet<>();

		if (pRoles == null) {
			Role userRole = roleService.findByType(RoleType.ROLE_ADMIN);
			roles.add(userRole);
		} else {
			pRoles.forEach(roleStr -> {
				if (roleStr.equals(RoleType.ROLE_ADMIN.getName())) { // Administrator
					Role adminRole = roleService.findByType(RoleType.ROLE_ADMIN);
					roles.add(adminRole);

				} else {
					Role userRole = roleService.findByType(RoleType.ROLE_ADMIN);
					roles.add(userRole);
				}
			});
		}

		return roles;
	}


}
