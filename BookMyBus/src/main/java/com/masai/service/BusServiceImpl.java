package com.masai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.masai.repository.BusRepository;
import com.masai.model.Bus;
import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;

import java.util.List;
import java.util.Optional;

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
		Optional<Bus> optionalBus = busRepository.findById(busId);
		return optionalBus.orElseThrow(() -> new ResourceNotFoundException("Bus not found with ID: " + busId));
	}

	@Override
	public Bus saveBus(Bus bus) throws ValidationException, SomethingWentWrongException {
		if (bus.getSeats() <= 0) {
			throw new ValidationException("Seats should be at least 1");
		}
		if (bus.getAvailableSeats() < 0) {
			throw new ValidationException("Available seats cannot be negative");
		}
		return busRepository.save(bus);
	}

	@Override
	public void deleteBus(Integer busId) throws ResourceNotFoundException, SomethingWentWrongException {
		Bus bus = getBusById(busId);
		busRepository.delete(bus);
	}

}
