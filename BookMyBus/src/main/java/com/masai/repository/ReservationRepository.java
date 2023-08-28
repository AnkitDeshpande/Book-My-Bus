package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.model.Reservations;

public interface ReservationRepository extends JpaRepository<Reservations, Integer> {

}
