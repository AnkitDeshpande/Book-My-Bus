package com.masai.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.masai.model.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer> {

	@Query("SELECT b FROM Bus b WHERE b.route.routeId = :routeId")
	 List<Bus> findBusesByRoute_RouteId(Integer routeId);

	List<Bus> findBusesByAvailableSeatsGreaterThan(int i);

	List<Bus> findBusesByArrivalTimeBetween(LocalTime startTime, LocalTime endTime);

	List<Bus> findBusesByDepartureTimeBetween(LocalTime startTime, LocalTime endTime);

}
