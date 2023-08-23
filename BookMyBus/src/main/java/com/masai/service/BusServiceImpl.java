package com.masai.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Bus updateBus(Integer busId, Bus updatedBus)
			throws ResourceNotFoundException, ValidationException, SomethingWentWrongException {
		Bus existingBus = getBusById(busId);
		existingBus.setBusName(updatedBus.getBusName());
		existingBus.setImage(updatedBus.getImage());
		existingBus.setDriverName(updatedBus.getDriverName());
		existingBus.setBusType(updatedBus.getBusType());
		existingBus.setRouteFrom(updatedBus.getRouteFrom());
		existingBus.setRouteTo(updatedBus.getRouteTo());
		existingBus.setArrivalTime(updatedBus.getArrivalTime());
		existingBus.setDepartureTime(updatedBus.getDepartureTime());
		existingBus.setSeats(updatedBus.getSeats());
		existingBus.setAvailableSeats(updatedBus.getAvailableSeats());
		existingBus.setDeleted(updatedBus.isDeleted());
		existingBus.setRoute(updatedBus.getRoute());
		return busRepository.save(existingBus);
	}

	@Override
	public List<Bus> getBusesByRoute(Integer routeId) {
		List<Bus> buses = busRepository.findBusesByRoute_RouteId(routeId);
		return Optional.of(buses).filter(list -> !list.isEmpty())
				.orElseThrow(() -> new ResourceNotFoundException("No buses found for route with ID: " + routeId));
	}

	@Override
	public List<Bus> getAvailableBuses() {
		List<Bus> buses = busRepository.findBusesByAvailableSeatsGreaterThan(0);
		return Optional.of(buses).filter(list -> !list.isEmpty())
				.orElseThrow(() -> new ResourceNotFoundException("No available buses found."));
	}

	@Override
	public List<Bus> getBusesWithAvailableSeats() {
		List<Bus> buses = busRepository.findBusesByAvailableSeatsGreaterThan(0);
		return Optional.of(buses).filter(list -> !list.isEmpty())
				.orElseThrow(() -> new ResourceNotFoundException("No buses with available seats found."));
	}

	@Override
	public List<Bus> getBusesByArrivalTimeRange(LocalTime startTime, LocalTime endTime) {
		List<Bus> buses = busRepository.findBusesByArrivalTimeBetween(startTime, endTime);
		return Optional.of(buses).filter(list -> !list.isEmpty()).orElseThrow(
				() -> new ResourceNotFoundException("No buses found within the specified arrival time range."));
	}

	@Override
	public List<Bus> getBusesByDepartureTimeRange(LocalTime startTime, LocalTime endTime) {
		List<Bus> buses = busRepository.findBusesByDepartureTimeBetween(startTime, endTime);
		return Optional.of(buses).filter(list -> !list.isEmpty()).orElseThrow(
				() -> new ResourceNotFoundException("No buses found within the specified departure time range."));
	}

	@Override
	public Page<Bus> getAllBuses(Pageable pageable) throws SomethingWentWrongException {
		return busRepository.findAll(pageable);
	}

}
