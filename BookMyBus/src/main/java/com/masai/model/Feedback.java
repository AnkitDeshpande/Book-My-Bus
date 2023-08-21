package com.masai.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer feedbackId;

	@Min(value = 1, message = "Driver rating should be between 1 and 10")
	@Max(value = 10, message = "Driver rating should be between 1 and 10")
	private Integer driverRating;

	@Min(value = 1, message = "Service rating should be between 1 and 10")
	@Max(value = 10, message = "Service rating should be between 1 and 10")
	private Integer serviceRating;

	@Min(value = 1, message = "Overall rating should be between 1 and 10")
	@Max(value = 10, message = "Overall rating should be between 1 and 10")
	private Integer overallRating;

	private String comments;

	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	@PastOrPresent(message = "Feedback date should be current date or in the past")
	private LocalDate feedbackDate = LocalDate.now();

	@JsonIgnore
	@ManyToOne
	private User user;

	@JsonIgnore
	@OneToOne
	private Bus bus;

	private boolean deleted = false;

	public Feedback(
			@Min(value = 1, message = "Driver rating should be between 1 and 10") @Max(value = 10, message = "Driver rating should be between 1 and 10") Integer driverRating,
			@Min(value = 1, message = "Service rating should be between 1 and 10") @Max(value = 10, message = "Service rating should be between 1 and 10") Integer serviceRating,
			@Min(value = 1, message = "Overall rating should be between 1 and 10") @Max(value = 10, message = "Overall rating should be between 1 and 10") Integer overallRating,
			String comments, User user, Bus bus) {
		super();
		this.driverRating = driverRating;
		this.serviceRating = serviceRating;
		this.overallRating = overallRating;
		this.comments = comments;
		this.user = user;
		this.bus = bus;
	}

}
