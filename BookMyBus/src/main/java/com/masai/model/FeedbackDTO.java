package com.masai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {

	private Integer driverRating;

	private Integer serviceRating;

	private Integer overallRating;

	private String comments;

	private Integer userId;

	private Integer busId;

}
