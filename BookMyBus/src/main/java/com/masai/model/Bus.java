package com.masai.model;

import java.time.LocalTime;
import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

	@URL(message = "Image URL should be in a valid format")
	private String image;

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
	@JoinColumn(name = "route_id")
	private Route route;

	public Bus(@NotBlank(message = "Bus name is mandatory") String busName,
			@URL(message = "Image URL should be in a valid format") String image,
			@NotBlank(message = "Driver name is mandatory") String driverName,
			@NotBlank(message = "Bus type is mandatory") String busType,
			@NotBlank(message = "Route from is mandatory") String routeFrom,
			@NotBlank(message = "Route to is mandatory") String routeTo,
			@NotNull(message = "Arrival time is mandatory") LocalTime arrivalTime,
			@NotNull(message = "Departure time is mandatory") LocalTime departureTime,
			@Min(value = 1, message = "Seats should be at least 1") Integer seats,
			@Min(value = 0, message = "Available seats cannot be negative") Integer availableSeats, boolean deleted,
			Route route) {
		super();
		this.busName = busName;
		this.image = image;
		this.driverName = driverName;
		this.busType = busType;
		this.routeFrom = routeFrom;
		this.routeTo = routeTo;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.seats = seats;
		this.availableSeats = availableSeats;
		this.deleted = deleted;
		this.route = route;
	}

}


/*
 * { "busName": "GreenLine Express", "driverName": "Michael Smith", "busType":
 * "Sleeper", "routeFrom": "New York", "routeTo": "Los Angeles", "arrivalTime":
 * "09:00:00", "departureTime": "18:00:00", "seats": 50, "availableSeats": 50 }
 */
