package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.masai.model.Bus;
import com.masai.service.BusService;

import jakarta.validation.Valid;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import java.util.List;

@RestController
public class BusController {

	@Autowired
	private BusService busService;

	@GetMapping("/api/buses")
	public ResponseEntity<List<Bus>> getAllBuses() {
		List<Bus> buses = busService.getAllBuses();
		return new ResponseEntity<>(buses, HttpStatus.OK);
	}

	@GetMapping("/api/buses/{id}")
	public ResponseEntity<Bus> getBusById(@Valid @PathVariable Integer id) throws ResourceNotFoundException {
		Bus bus = busService.getBusById(id);
		return new ResponseEntity<>(bus, HttpStatus.OK);
	}

	@PostMapping("/api/buses")
	public ResponseEntity<Bus> createBus(@Valid @RequestBody Bus bus) throws ValidationException, SomethingWentWrongException {
		Bus createdBus = busService.saveBus(bus);
		return new ResponseEntity<>(createdBus, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/buses/{id}")
	public ResponseEntity<String> deleteBus(@Valid @PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		busService.deleteBus(id);
		return new ResponseEntity<>("Deleted",HttpStatus.NO_CONTENT);
	}
}
