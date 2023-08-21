package com.masai.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer reservationId;

	private String reservationStatus;

	private String reservationType;

	@FutureOrPresent(message = "Date should not be in past *")
	@NotNull(message = "Reservation Date is mandatory *")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	private LocalDate reservationDate;

	@NotNull(message = "Reservation Time is mandatory *")
	@JsonFormat(pattern = "hh-mm-ss", shape = Shape.STRING)
	private LocalTime reservationTime;

	@NotNull(message = "Reservation source is mandatory *")
	private String source;

	@NotBlank(message = "Reservation destination not be ramain empty *")
	private String destination;

	@OneToOne
	private Bus bus;

	public Reservation(Integer reservationId,
			@Future(message = "Date should not be in past *") LocalDate reservationDate, String source,
			String destination) {
		super();
		this.reservationId = reservationId;
		this.reservationDate = reservationDate;
		this.source = source;
		this.destination = destination;
	}

	public Reservation() {

	}

	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", reservationStatus=" + reservationStatus
				+ ", reservationType=" + reservationType + ", reservationDate=" + reservationDate + ", reservationTime="
				+ reservationTime + ", source=" + source + ", destination=" + destination + "]";
	}

}
