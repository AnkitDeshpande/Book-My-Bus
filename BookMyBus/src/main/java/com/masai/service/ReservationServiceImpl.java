package com.masai.service;

import java.util.List;
import java.util.Optional;

import com.masai.model.Reservations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Reservations;
import com.masai.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<Reservations> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservations getReservationById(Integer reservationId) throws ResourceNotFoundException {
        Optional<Reservations> optionalReservation = reservationRepository.findById(reservationId);
        return optionalReservation.orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + reservationId));
    }

    @Override
    public Reservations saveReservation(Reservations reservation)
            throws ValidationException, SomethingWentWrongException {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Integer reservationId) throws ResourceNotFoundException, SomethingWentWrongException {
        Reservations reservation = getReservationById(reservationId);
        reservationRepository.delete(reservation);
    }
}

