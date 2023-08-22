package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Reservation;
import com.masai.repository.ReservationRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Integer reservationId) throws ResourceNotFoundException {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        return optionalReservation.orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + reservationId));
    }

    @Override
    public Reservation saveReservation(Reservation reservation)
            throws ValidationException, SomethingWentWrongException {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Integer reservationId) throws ResourceNotFoundException, SomethingWentWrongException {
        Reservation reservation = getReservationById(reservationId);
        reservationRepository.delete(reservation);
    }
}

