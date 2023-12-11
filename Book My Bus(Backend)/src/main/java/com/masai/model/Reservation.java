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
//import com.masai.exception.CustomDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;

	private String reservationStatus;

	private String reservationType;

	@Future(message = "Date should not be in past *")
//	@NotNull(message = "Reservation Date is mandatory *")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	private LocalDate reservationDate=LocalDate.now().plusDays(1);

//	@NotNull(message = "Reservation Time is mandatory *")
//	@JsonFormat(pattern = "hh-mm-ss", shape = Shape.STRING)
	private String reservationTime=LocalTime.now().toString();

//	@NotNull(message = "Reservation source is mandatory *")
	private String source;

//	@NotBlank(message = "Reservation destination not be ramain empty *")
//	@NotNull(message = "Reservation destination is mandatory *")
	private String destination;

	@OneToOne
	private Bus bus;

	@OneToOne
	private User user;

}
