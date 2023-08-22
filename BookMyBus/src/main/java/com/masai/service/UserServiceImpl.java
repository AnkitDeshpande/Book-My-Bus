package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.User;
import com.masai.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Integer userId) throws ResourceNotFoundException {
		Optional<User> optionalUser = userRepository.findById(userId);
		return optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
	}

	@Override
	public User saveUser(User user) throws ValidationException, SomethingWentWrongException {
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Integer userId) throws ResourceNotFoundException, SomethingWentWrongException {
		User user = getUserById(userId);
		userRepository.delete(user);
	}

}
