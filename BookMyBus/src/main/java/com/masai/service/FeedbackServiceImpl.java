package com.masai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Bus;
import com.masai.model.Feedback;
import com.masai.model.FeedbackDTO;
import com.masai.model.User;
import com.masai.repository.BusRepository;
import com.masai.repository.FeedbackRepository;
import com.masai.repository.UserRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BusRepository busRepo;

	@Override
	public List<Feedback> getAllFeedbacks() {
		return feedbackRepository.findAll();
	}

	@Override
	public Feedback getFeedbackById(Integer feedbackId) throws ResourceNotFoundException {
		Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackId);
		return optionalFeedback
				.orElseThrow(() -> new ResourceNotFoundException("Feedback not found with ID: " + feedbackId));
	}

	@Override
	public Feedback saveFeedback(FeedbackDTO feedback) throws ValidationException, SomethingWentWrongException {
		Bus bus = busRepo.findById(feedback.getBusId())
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + feedback.getUserId()));
		User user = userRepo.findById(feedback.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("Bus not found with ID: " + feedback.getBusId()));

		Feedback fd = new Feedback(feedback.getDriverRating(), feedback.getServiceRating(), feedback.getOverallRating(),
				feedback.getComments(), user, bus);
		return feedbackRepository.save(fd);
	}

	@Override
	public void deleteFeedback(Integer feedbackId) throws ResourceNotFoundException, SomethingWentWrongException {
		Feedback feedback = getFeedbackById(feedbackId);
		feedbackRepository.delete(feedback);
	}

	@Override
	public List<Feedback> getFeedbacksByBusId(Integer busId) throws SomethingWentWrongException {
		try {
			return feedbackRepository.findByBus_Id(busId);
		} catch (Exception e) {
			throw new SomethingWentWrongException("Something went wrong while fetching feedbacks by bus ID.");
		}
	}

	@Override
	public List<Feedback> getFeedbacksByUserId(Integer userId) throws SomethingWentWrongException {
		try {
			return feedbackRepository.findByUser_Id(userId);
		} catch (Exception e) {
			throw new SomethingWentWrongException("Something went wrong while fetching feedbacks by user ID.");
		}
	}

	@Override
	public List<Feedback> getFeedbacksAboveRating(Integer rating) throws SomethingWentWrongException {
		try {
			return feedbackRepository.findByOverallRatingGreaterThanEqual(rating);
		} catch (Exception e) {
			throw new SomethingWentWrongException("Something went wrong while fetching feedbacks above rating.");
		}
	}

	@Override
	public List<Feedback> getFeedbacksBelowRating(Integer rating) throws SomethingWentWrongException {
		try {
			return feedbackRepository.findByOverallRatingLessThanEqual(rating);
		} catch (Exception e) {
			throw new SomethingWentWrongException("Something went wrong while fetching feedbacks below rating.");
		}
	}

	@Override
	public List<Feedback> getDeletedFeedbacks() throws SomethingWentWrongException {
		try {
			return feedbackRepository.findByDeletedTrue();
		} catch (Exception e) {
			throw new SomethingWentWrongException("Something went wrong while fetching deleted feedbacks.");
		}
	}

	@Override
	public List<Feedback> getActiveFeedbacks() throws SomethingWentWrongException {
		try {
			return feedbackRepository.findByDeletedFalse();
		} catch (Exception e) {
			throw new SomethingWentWrongException("Something went wrong while fetching active feedbacks.");
		}
	}

	@Override
	public List<Feedback> saveBulkFeedbacks(List<FeedbackDTO> feedbacks)
			throws ValidationException, SomethingWentWrongException {
		List<Feedback> createdFeedbacks = new ArrayList<>();
		for (FeedbackDTO feedback : feedbacks) {
			Bus bus = busRepo.findById(feedback.getBusId()).orElseThrow(
					() -> new ResourceNotFoundException("User not found with ID: " + feedback.getUserId()));
			User user = userRepo.findById(feedback.getUserId())
					.orElseThrow(() -> new ResourceNotFoundException("Bus not found with ID: " + feedback.getBusId()));

			Feedback fd = new Feedback(feedback.getDriverRating(), feedback.getServiceRating(),
					feedback.getOverallRating(), feedback.getComments(), user, bus);
			feedbackRepository.save(fd);
			createdFeedbacks.add(fd);
		}
		return createdFeedbacks;
	}

}

/**
 * [ {
 * 
 * "driverRating": 10, "serviceRating": 10, "overallRating": 10, "comments":
 * "Worst", "busId": 5, "userId":1 }, {
 * 
 * "driverRating": 10, "serviceRating": 10, "overallRating": 10, "comments":
 * "average", "busId": 6, "userId":1 } ]
 */
