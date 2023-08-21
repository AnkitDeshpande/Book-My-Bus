package com.masai.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer reservationId;

	@NotBlank(message = "Reservation status is mandatory")
	private String reservationStatus;

	@NotBlank(message = "Reservation type is mandatory")
	private String reservationType;

	@FutureOrPresent(message = "Reservation date should not be in the past")
	@NotNull(message = "Reservation date is mandatory")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private LocalDate reservationDate;

	@NotNull(message = "Reservation time is mandatory")
	@JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
	private LocalTime reservationTime;

	@NotBlank(message = "Source is mandatory")
	private String source;

	@NotBlank(message = "Destination is mandatory")
	private String destination;

	@ManyToOne
	private Bus bus;
	
    @ManyToOne
    private User user;

	private boolean deleted = false;

	public Reservation(@NotBlank(message = "Reservation status is mandatory") String reservationStatus,
	        @NotBlank(message = "Reservation type is mandatory") String reservationType,
	        @FutureOrPresent(message = "Reservation date should not be in the past") @NotNull(message = "Reservation date is mandatory") LocalDate reservationDate,
	        @NotNull(message = "Reservation time is mandatory") LocalTime reservationTime,
	        @NotBlank(message = "Source is mandatory") String source,
	        @NotBlank(message = "Destination is mandatory") String destination, Bus bus) {
		super();
		this.reservationStatus = reservationStatus;
		this.reservationType = reservationType;
		this.reservationDate = reservationDate;
		this.reservationTime = reservationTime;
		this.source = source;
		this.destination = destination;
		this.bus = bus;
	}

}
