package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.masai.model.FeedbackDTO;
import com.masai.service.FeedbackService;

import jakarta.validation.Valid;
@CrossOrigin
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
	public ResponseEntity<Feedback> createFeedback(@Valid @RequestBody FeedbackDTO feedback)
			throws ValidationException, SomethingWentWrongException {
		
		Feedback createdFeedback = feedbackService.saveFeedback(feedback);
		return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
	}

	@PostMapping("/api/feedbacks/bulk")
	public ResponseEntity<String> createBulkFeedbacks(@Valid @RequestBody List<FeedbackDTO> feedbacks)
			throws ValidationException, SomethingWentWrongException {
		List<Feedback> createdFeedbacks = feedbackService.saveBulkFeedbacks(feedbacks);
		if (!createdFeedbacks.isEmpty()) {
			return new ResponseEntity<>("Bulk feedbacks added successfully", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Bulk feedbacks could not be added", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/api/feedbacks/{id}")
	public ResponseEntity<String> deleteFeedback(@Valid @PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		feedbackService.deleteFeedback(id);
		return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/api/feedbacks/bus/{busId}")
	public ResponseEntity<List<Feedback>> getFeedbacksByBusId(@Valid @PathVariable Integer busId) {
		List<Feedback> feedbacks = feedbackService.getFeedbacksByBusId(busId);
		return new ResponseEntity<>(feedbacks, HttpStatus.OK);
	}

	@GetMapping("/api/feedbacks/user/{userId}")
	public ResponseEntity<List<Feedback>> getFeedbacksByUserId(@Valid @PathVariable Integer userId) {
		List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
		return new ResponseEntity<>(feedbacks, HttpStatus.OK);
	}

	@GetMapping("/api/feedbacks/rating-above/{rating}")
	public ResponseEntity<List<Feedback>> getFeedbacksAboveRating(@Valid @PathVariable Integer rating) {
		List<Feedback> feedbacks = feedbackService.getFeedbacksAboveRating(rating);
		return new ResponseEntity<>(feedbacks, HttpStatus.OK);
	}

	@GetMapping("/api/feedbacks/rating-below/{rating}")
	public ResponseEntity<List<Feedback>> getFeedbacksBelowRating(@Valid @PathVariable Integer rating) {
		List<Feedback> feedbacks = feedbackService.getFeedbacksBelowRating(rating);
		return new ResponseEntity<>(feedbacks, HttpStatus.OK);
	}

	@GetMapping("/api/feedbacks/deleted")
	public ResponseEntity<List<Feedback>> getDeletedFeedbacks() {
		List<Feedback> feedbacks = feedbackService.getDeletedFeedbacks();
		return new ResponseEntity<>(feedbacks, HttpStatus.OK);
	}

	@GetMapping("/api/feedbacks/active")
	public ResponseEntity<List<Feedback>> getActiveFeedbacks() {
		List<Feedback> feedbacks = feedbackService.getActiveFeedbacks();
		return new ResponseEntity<>(feedbacks, HttpStatus.OK);
	}
}
