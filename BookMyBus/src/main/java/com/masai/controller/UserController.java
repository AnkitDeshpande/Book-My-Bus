package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.User;
import com.masai.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/api/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/api/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Integer id) throws ResourceNotFoundException {
		User user = userService.getUserById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/api/users")
    public ResponseEntity<User> createUser(@RequestBody User user) throws ValidationException, SomethingWentWrongException {
        User createdUser = userService.saveUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

	@DeleteMapping("/api/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		userService.deleteUser(id);
		return new ResponseEntity<>("Deleted.", HttpStatus.NO_CONTENT);
	}
}
