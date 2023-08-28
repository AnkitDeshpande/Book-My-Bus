package com.masai.controller;

import java.util.List;

import com.masai.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/registerUser")
	public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) throws ValidationException, SomethingWentWrongException {
		Users createdUser = userService.saveUser(user);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<Users>> getAllUsers() {
		List<Users> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/getUserId/{id}")
	public ResponseEntity<Users> getUserById(@Valid @PathVariable Integer id) throws ResourceNotFoundException {
		Users user = userService.getUserById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}



	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<String> deleteUser(@Valid @PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		userService.deleteUser(id);
		return new ResponseEntity<>("Deleted.", HttpStatus.NO_CONTENT);
	}
}
