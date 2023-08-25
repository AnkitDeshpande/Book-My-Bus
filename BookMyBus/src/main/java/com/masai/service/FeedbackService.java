package com.masai.service;

import java.util.List;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Feedback;
import com.masai.model.FeedbackDTO;

import java.util.List;

/**
 * Service interface for managing feedback related operations.
 */
public interface FeedbackService {

	/**
	 * Retrieves all feedbacks.
	 *
	 * @return List of all feedbacks.
	 */
	List<Feedback> getAllFeedbacks();

	/**
	 * Retrieves a feedback by its ID.
	 *
	 * @param feedbackId The ID of the feedback to retrieve.
	 * @return The retrieved feedback.
	 * @throws ResourceNotFoundException If the feedback with the given ID is not
	 *                                   found.
	 */
	Feedback getFeedbackById(Integer feedbackId) throws ResourceNotFoundException;

	/**
	 * Saves a new feedback.
	 *
	 * @param feedback The feedback data to be saved.
	 * @return The saved feedback.
	 * @throws ValidationException         If the provided feedback data is not
	 *                                     valid.
	 * @throws SomethingWentWrongException If an unexpected error occurs during the
	 *                                     operation.
	 */
	Feedback saveFeedback(FeedbackDTO feedback) throws ValidationException, SomethingWentWrongException;

	/**
	 * Deletes a feedback by its ID.
	 *
	 * @param feedbackId The ID of the feedback to delete.
	 * @throws ResourceNotFoundException   If the feedback with the given ID is not
	 *                                     found.
	 * @throws SomethingWentWrongException If an unexpected error occurs during the
	 *                                     operation.
	 */
	void deleteFeedback(Integer feedbackId) throws ResourceNotFoundException, SomethingWentWrongException;

	/**
	 * Saves a list of new feedbacks in bulk.
	 *
	 * @param feedbacks The list of feedback data to be saved.
	 * @return The list of saved feedbacks.
	 * @throws ValidationException         If any of the provided feedback data is
	 *                                     not valid.
	 * @throws SomethingWentWrongException If an unexpected error occurs during the
	 *                                     operation.
	 */
	List<Feedback> saveBulkFeedbacks(List<FeedbackDTO> feedbacks)
			throws ValidationException, SomethingWentWrongException;

	/**
	 * Retrieves all active feedbacks.
	 *
	 * @return List of active feedbacks.
	 * @throws SomethingWentWrongException If an unexpected error occurs during the
	 *                                     operation.
	 */
	List<Feedback> getActiveFeedbacks() throws SomethingWentWrongException;

	/**
	 * Retrieves feedbacks associated with a specific bus.
	 *
	 * @param busId The ID of the bus.
	 * @return List of feedbacks associated with the specified bus.
	 * @throws SomethingWentWrongException If an unexpected error occurs during the
	 *                                     operation.
	 */
	List<Feedback> getFeedbacksByBusId(Integer busId) throws SomethingWentWrongException;

	/**
	 * Retrieves feedbacks associated with a specific user.
	 *
	 * @param userId The ID of the user.
	 * @return List of feedbacks associated with the specified user.
	 * @throws SomethingWentWrongException If an unexpected error occurs during the
	 *                                     operation.
	 */
	List<Feedback> getFeedbacksByUserId(Integer userId) throws SomethingWentWrongException;

	/**
	 * Retrieves feedbacks with ratings above a given threshold.
	 *
	 * @param rating The minimum rating threshold.
	 * @return List of feedbacks with ratings above the threshold.
	 * @throws SomethingWentWrongException If an unexpected error occurs during the
	 *                                     operation.
	 */
	List<Feedback> getFeedbacksAboveRating(Integer rating) throws SomethingWentWrongException;

	/**
	 * Retrieves all deleted feedbacks.
	 *
	 * @return List of deleted feedbacks.
	 * @throws SomethingWentWrongException If an unexpected error occurs during the
	 *                                     operation.
	 */
	List<Feedback> getDeletedFeedbacks() throws SomethingWentWrongException;

	/**
	 * Retrieves feedbacks with ratings below a given threshold.
	 *
	 * @param rating The maximum rating threshold.
	 * @return List of feedbacks with ratings below the threshold.
	 * @throws SomethingWentWrongException If an unexpected error occurs during the
	 *                                     operation.
	 */
	List<Feedback> getFeedbacksBelowRating(Integer rating) throws SomethingWentWrongException;
}
