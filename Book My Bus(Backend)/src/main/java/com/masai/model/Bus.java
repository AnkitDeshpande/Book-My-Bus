package com.masai.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Bus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer busId;

	private String busName;

	private String driverName;

	private String busType;

	private String routeFrom;

	private String routeTo;

	private LocalTime arrivalTime;

	private LocalTime departureTime;

	private Integer seats;

	private Integer availableSeats;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "route_id")
	private Route route;

}
