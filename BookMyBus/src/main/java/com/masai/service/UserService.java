package com.masai.service;

import java.util.List;
import java.util.Optional;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Users;

public interface UserService {
	List<Users> getAllUsers();

	Optional<Users> findByEmail(String Email)throws ResourceNotFoundException;

	Users getUserById(Integer userId) throws ResourceNotFoundException;

	Users saveUser(Users user) throws ValidationException, SomethingWentWrongException;

	Users deleteUser(Integer userId) throws ResourceNotFoundException, SomethingWentWrongException;

}
