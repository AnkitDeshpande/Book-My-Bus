package com.masai.model;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Bus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer busId;

	@NotBlank(message = "Bus name is mandatory")
	private String busName;

	@NotBlank(message = "Driver name is mandatory")
	private String driverName;

	@NotBlank(message = "Bus type is mandatory")
	private String busType;

	@NotBlank(message = "Route from is mandatory")
	private String routeFrom;

	@NotBlank(message = "Route to is mandatory")
	private String routeTo;

	@NotNull(message = "Arrival time is mandatory")
	private LocalTime arrivalTime;

	@NotNull(message = "Departure time is mandatory")
	private LocalTime departureTime;

	@Min(value = 1, message = "Seats should be at least 1")
	private Integer seats;

	@Min(value = 0, message = "Available seats cannot be negative")
	private Integer availableSeats;

	private boolean deleted = false;

	@ManyToOne
	private Route route;

	public Bus(@NotBlank(message = "Bus name is mandatory") String busName,
			@NotBlank(message = "Driver name is mandatory") String driverName,
			@NotBlank(message = "Bus type is mandatory") String busType,
			@NotBlank(message = "Route from is mandatory") String routeFrom,
			@NotBlank(message = "Route to is mandatory") String routeTo,
			@NotNull(message = "Arrival time is mandatory") LocalTime arrivalTime,
			@NotNull(message = "Departure time is mandatory") LocalTime departureTime,
			@Min(value = 1, message = "Seats should be at least 1") Integer seats,
			@Min(value = 0, message = "Available seats cannot be negative") Integer availableSeats, Route route) {
		super();
		this.busName = busName;
		this.driverName = driverName;
		this.busType = busType;
		this.routeFrom = routeFrom;
		this.routeTo = routeTo;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.seats = seats;
		this.availableSeats = availableSeats;
		this.route = route;
	}
}