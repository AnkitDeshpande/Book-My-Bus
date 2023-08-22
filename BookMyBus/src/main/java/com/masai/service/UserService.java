package com.masai.service;

import java.util.List;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.User;

public interface UserService {
	List<User> getAllUsers();

	User getUserById(Integer userId) throws ResourceNotFoundException;

	User saveUser(User user) throws ValidationException, SomethingWentWrongException;

	void deleteUser(Integer userId) throws ResourceNotFoundException, SomethingWentWrongException;

}
