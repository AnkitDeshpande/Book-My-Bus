package com.masai.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Bus;
import com.masai.service.BusService;

import jakarta.validation.Valid;

@RestController
public class BusController {

	@Autowired
	private BusService busService;

	@GetMapping("/api/buses")
	public List<Bus> getAllBuses() {
		List<Bus> buses = busService.getAllBuses();
		
		return buses;
	}

	@GetMapping("/api/buses/{id}")
	public ResponseEntity<Bus> getBusById(@Valid @PathVariable Integer id) throws ResourceNotFoundException {
		Bus bus = busService.getBusById(id);
		return new ResponseEntity<>(bus, HttpStatus.OK);
	}

	@PostMapping("/api/buses")
	public ResponseEntity<Bus> createBus(@Valid @RequestBody Bus bus)
			throws ValidationException, SomethingWentWrongException {
		Bus createdBus = busService.saveBus(bus);
		return new ResponseEntity<>(createdBus, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/buses/{id}")
	public ResponseEntity<String> deleteBus(@Valid @PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		busService.deleteBus(id);
		return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
	}

	@PutMapping("/api/buses/{id}")
	public ResponseEntity<Bus> updateBus(@Valid @PathVariable Integer id, @Valid @RequestBody Bus updatedBus)
			throws ResourceNotFoundException, ValidationException, SomethingWentWrongException {
		Bus bus = busService.updateBus(id, updatedBus);
		return new ResponseEntity<>(bus, HttpStatus.OK);
	}

	/**
	 * URL: GET /api/buses/route/{routeId} Example:
	 * http://localhost:8080/api/buses/route/2
	 */
	@GetMapping("/api/buses/route/{routeId}")
	public ResponseEntity<List<Bus>> getBusesByRoute(@Valid @PathVariable Integer routeId) {
		List<Bus> buses = busService.getBusesByRoute(routeId);
		return new ResponseEntity<>(buses, HttpStatus.OK);
	}

	/**
	 * URL: GET /api/buses/available Example:
	 * http://localhost:8080/api/buses/available
	 */
	@GetMapping("/api/buses/available")
	public ResponseEntity<List<Bus>> getAvailableBuses() {
		List<Bus> availableBuses = busService.getAvailableBuses();
		return new ResponseEntity<>(availableBuses, HttpStatus.OK);
	}

	/**
	 * URL: GET /api/buses/available-seats Example:
	 * http://localhost:8080/api/buses/available-seats
	 */
	@GetMapping("/api/buses/available-seats")
	public ResponseEntity<List<Bus>> getBusesWithAvailableSeats() {
		List<Bus> busesWithAvailableSeats = busService.getBusesWithAvailableSeats();
		return new ResponseEntity<>(busesWithAvailableSeats, HttpStatus.OK);
	}

	/**
	 * URL: GET /api/buses/arrival-time Example:
	 * http://localhost:8080/api/buses/arrival-time?startTime=08:00&endTime=12:00
	 */
	@GetMapping("/api/buses/arrival-time")
	public ResponseEntity<List<Bus>> getBusesByArrivalTimeRange(
			@Valid @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
			@Valid @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime endTime) {
		List<Bus> buses = busService.getBusesByArrivalTimeRange(startTime, endTime);
		return new ResponseEntity<>(buses, HttpStatus.OK);
	}

	/**
	 * URL: GET /api/buses/departure-time Example:
	 * http://localhost:8080/api/buses/departure-time?startTime=15:00&endTime=22:00
	 */
	@GetMapping("/api/buses/departure-time")
	public ResponseEntity<List<Bus>> getBusesByDepartureTimeRange(
			@Valid @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,
			@Valid @RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime endTime) {
		List<Bus> buses = busService.getBusesByDepartureTimeRange(startTime, endTime);
		return new ResponseEntity<>(buses, HttpStatus.OK);
	}

	/**
	 * 
	 * @param pageable
	 * @return list of buses
	 * @url example : http://localhost:8080/api/page-buses?page=1&size=5
	 */
	@GetMapping("/api/page-buses")
	public ResponseEntity<Page<Bus>> getBuses(Pageable pageable) {
		Page<Bus> buses = busService.getAllBuses(pageable);
		return new ResponseEntity<>(buses, HttpStatus.OK);
	}
}
