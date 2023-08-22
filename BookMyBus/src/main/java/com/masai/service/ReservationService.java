package com.masai.service;

import java.util.List;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Reservation;

public interface ReservationService {
	List<Reservation> getAllReservations();

	Reservation getReservationById(Integer reservationId) throws ResourceNotFoundException;

	Reservation saveReservation(Reservation reservation) throws ValidationException, SomethingWentWrongException;

	void deleteReservation(Integer reservationId) throws ResourceNotFoundException, SomethingWentWrongException;

}
