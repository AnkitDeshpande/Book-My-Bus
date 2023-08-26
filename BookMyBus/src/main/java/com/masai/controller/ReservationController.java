package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Reservation;
import com.masai.service.ReservationService;

import jakarta.validation.Valid;

@CrossOrigin
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
	public ResponseEntity<Reservation> getReservationById(@Valid @PathVariable Integer id) throws ResourceNotFoundException {
		Reservation reservation = reservationService.getReservationById(id);
		return new ResponseEntity<>(reservation, HttpStatus.OK);
	}

	@PostMapping("/api/reservations")
	public ResponseEntity<Reservation> createReservation(@Valid @RequestBody Reservation reservation)
			throws ValidationException, SomethingWentWrongException {
		Reservation createdReservation = reservationService.saveReservation(reservation);
		return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/reservations/{id}")
	public ResponseEntity<String> deleteReservation(@Valid @PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		reservationService.deleteReservation(id);
		return new ResponseEntity<>("Deleted.", HttpStatus.NO_CONTENT);
	}

}
