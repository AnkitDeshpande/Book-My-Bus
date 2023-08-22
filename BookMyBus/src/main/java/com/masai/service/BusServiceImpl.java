package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Bus;
import com.masai.repository.BusRepository;

@Service
public class BusServiceImpl implements BusService {

	@Autowired
	private BusRepository busRepository;

	@Override
	public List<Bus> getAllBuses() {
		return busRepository.findAll();
	}

	@Override
	public Bus getBusById(Integer busId) throws ResourceNotFoundException {
		return null;
	}

	@Override
	public Bus saveBus(Bus bus) throws ValidationException, SomethingWentWrongException {
		return busRepository.save(bus);
	}

	@Override
	public void deleteBus(Integer busId) throws ResourceNotFoundException, SomethingWentWrongException {

	}

}
