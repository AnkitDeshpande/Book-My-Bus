package com.masai.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Bus;

public interface BusService {
	List<Bus> getAllBuses();

	Bus getBusById(Integer busId) throws ResourceNotFoundException;

	Bus saveBus(Bus bus) throws ValidationException, SomethingWentWrongException;

	void deleteBus(Integer busId) throws ResourceNotFoundException, SomethingWentWrongException;

	Bus updateBus(Integer busId, Bus updatedBus)
			throws ResourceNotFoundException, ValidationException, SomethingWentWrongException;

	Page<Bus> getAllBuses(Pageable pageable) throws SomethingWentWrongException;

	List<Bus> getBusesByRoute(Integer routeId) throws SomethingWentWrongException;

	List<Bus> getAvailableBuses() throws SomethingWentWrongException;

	List<Bus> getBusesWithAvailableSeats() throws SomethingWentWrongException;

	List<Bus> getBusesByArrivalTimeRange(LocalTime startTime, LocalTime endTime) throws SomethingWentWrongException;

	List<Bus> getBusesByDepartureTimeRange(LocalTime startTime, LocalTime endTime) throws SomethingWentWrongException;
}
