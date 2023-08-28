package com.masai.service;

import java.util.List;
import java.util.Optional;

import com.masai.model.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Users> getAllUsers() throws ResourceNotFoundException{
		List<Users> list = userRepository.findAll();
		if(list.isEmpty())throw new ResourceNotFoundException("No user found");
		return list;
	}

	@Override
	public Optional<Users> findByEmail(String Email) throws ResourceNotFoundException {
		Optional<Users>optional = userRepository.findByEmail(Email);
		if(optional.isEmpty()){
			throw new ResourceNotFoundException("No user found with email "+Email);
		}
		return optional;
	}

	@Override
	public Users getUserById(Integer userId) throws ResourceNotFoundException {
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
	}

	@Override
	public Users saveUser(Users users) throws ValidationException, SomethingWentWrongException {
		Optional<Users>optional = userRepository.findByEmail(users.getEmail());
		if(optional.isPresent()){
			throw new ResourceNotFoundException("User already registered with email "+users.getEmail());
		}
		return userRepository.save(users);
	}

	@Override
	public Users deleteUser(Integer userId) throws ResourceNotFoundException, SomethingWentWrongException {
		Optional<Users>optional = userRepository.findById(userId);
		if(optional.isEmpty()){
			throw new ResourceNotFoundException("No user found with userId "+userId);
		}
		Users u = optional.get();
		u.setDeleted(true);
		return u;
	}

}
