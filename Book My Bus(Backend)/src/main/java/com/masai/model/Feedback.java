package com.masai.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Entity
@Data
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer feedbackId;

	@Min(value = 1, message = "Rating should be between 1 to 10")
	@Max(value = 10, message = "Rating should be between 1 to 10")
	private Integer driverRating;

	@Min(value = 1, message = "Rating should be between 1 to 10")
	@Max(value = 10, message = "Rating should be between 1 to 10")
	private Integer serviceRating;

	@Min(value = 1, message = "Rating should be between 1 to 10")
	@Max(value = 10, message = "Rating should be between 1 to 10")
	private Integer overallRating;

	private String comments;

	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	@PastOrPresent(message = "Date should be current date or past date")
	private LocalDate feedbackDate = LocalDate.now();

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "bus_id")
	private Bus bus;

}
