package com.masai.controller;

import com.masai.model.Reservations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.masai.model.Reservations;
import com.masai.service.ReservationService;

import jakarta.validation.Valid;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import java.util.List;

@RestController
public class ReservationController {
	@Autowired
	private ReservationService reservationService;

	@GetMapping("/api/reservations")
	public ResponseEntity<List<Reservations>> getAllReservations() {
		List<Reservations> reservations = reservationService.getAllReservations();
		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}

	@GetMapping("/api/reservations/{id}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	public ResponseEntity<Reservations> getReservationById(@Valid @PathVariable Integer id) throws ResourceNotFoundException {
		Reservations reservation = reservationService.getReservationById(id);
		return new ResponseEntity<>(reservation, HttpStatus.OK);
	}

	@PostMapping("/api/reservations")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Reservations> createReservation(@Valid @RequestBody Reservations reservation)
			throws ValidationException, SomethingWentWrongException {
		Reservations createdReservation = reservationService.saveReservation(reservation);
		return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/reservations/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteReservation(@Valid @PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		reservationService.deleteReservation(id);
		return new ResponseEntity<>("Deleted.", HttpStatus.NO_CONTENT);
	}

}
