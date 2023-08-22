package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.masai.model.Reservation;
import com.masai.service.ReservationService;
import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import java.util.List;

@RestController
public class ReservationController {
	@Autowired
	private ReservationService reservationService;

	@GetMapping("/api/reservations")
	public ResponseEntity<List<Reservation>> getAllReservations() {
		List<Reservation> reservations = reservationService.getAllReservations();
		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}

	@GetMapping("/api/reservations/{id}")
	public ResponseEntity<Reservation> getReservationById(@PathVariable Integer id) throws ResourceNotFoundException {
		Reservation reservation = reservationService.getReservationById(id);
		return new ResponseEntity<>(reservation, HttpStatus.OK);
	}

	@PostMapping("/api/reservations")
	public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation)
			throws ValidationException, SomethingWentWrongException {
		Reservation createdReservation = reservationService.saveReservation(reservation);
		return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/reservations/{id}")
	public ResponseEntity<String> deleteReservation(@PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		reservationService.deleteReservation(id);
		return new ResponseEntity<>("Deleted.", HttpStatus.NO_CONTENT);
	}

}
