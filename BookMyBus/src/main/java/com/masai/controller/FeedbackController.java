package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Feedback;
import com.masai.service.FeedbackService;

import jakarta.validation.Valid;

@RestController
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackService;

	@GetMapping("/api/feedbacks")
	public ResponseEntity<List<Feedback>> getAllFeedbacks() {
		List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
		return new ResponseEntity<>(feedbacks, HttpStatus.OK);
	}

	@GetMapping("/api/feedbacks/{id}")
	public ResponseEntity<Feedback> getFeedbackById(@Valid @PathVariable Integer id) throws ResourceNotFoundException {
		Feedback feedback = feedbackService.getFeedbackById(id);
		return new ResponseEntity<>(feedback, HttpStatus.OK);
	}

	@PostMapping("/api/feedbacks")
	public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody Feedback feedback)
			throws ValidationException, SomethingWentWrongException {
		Feedback createdFeedback = feedbackService.saveFeedback(feedback);
		return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/feedbacks/{id}")
	public ResponseEntity<String> deleteFeedback(@Valid @PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		feedbackService.deleteFeedback(id);
		return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
	}
}
