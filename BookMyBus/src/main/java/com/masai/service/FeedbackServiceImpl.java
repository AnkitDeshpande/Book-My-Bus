package com.masai.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Feedback;
import com.masai.repository.FeedbackRepository;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	private FeedbackRepository feedbackRepository;

	@Override
	public List<Feedback> getAllFeedbacks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Feedback getFeedbackById(Integer feedbackId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Feedback saveFeedback(Feedback feedback) throws ValidationException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFeedback(Integer feedbackId) throws ResourceNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub

	}

}
