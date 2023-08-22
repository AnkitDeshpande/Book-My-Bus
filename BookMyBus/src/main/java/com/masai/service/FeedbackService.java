package com.masai.service;

import java.util.List;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Feedback;

public interface FeedbackService {
	List<Feedback> getAllFeedbacks();

	Feedback getFeedbackById(Integer feedbackId) throws ResourceNotFoundException;

	Feedback saveFeedback(Feedback feedback) throws ValidationException, SomethingWentWrongException;

	void deleteFeedback(Integer feedbackId) throws ResourceNotFoundException, SomethingWentWrongException;

}
