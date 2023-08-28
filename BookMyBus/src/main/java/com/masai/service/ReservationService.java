package com.masai.service;

import java.util.List;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;

import com.masai.model.Reservations;

public interface ReservationService {
	List<Reservations> getAllReservations();

	Reservations getReservationById(Integer reservationId) throws ResourceNotFoundException;

	Reservations saveReservation(Reservations reservation) throws ValidationException, SomethingWentWrongException;

	void deleteReservation(Integer reservationId) throws ResourceNotFoundException, SomethingWentWrongException;

}
