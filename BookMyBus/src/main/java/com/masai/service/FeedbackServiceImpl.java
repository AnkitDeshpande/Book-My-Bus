package com.masai.service;

import java.util.List;
import java.util.Optional;

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
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback getFeedbackById(Integer feedbackId) throws ResourceNotFoundException {
        Optional<Feedback> optionalFeedback = feedbackRepository.findById(feedbackId);
        return optionalFeedback.orElseThrow(() -> new ResourceNotFoundException("Feedback not found with ID: " + feedbackId));
    }

    @Override
    public Feedback saveFeedback(Feedback feedback) throws ValidationException, SomethingWentWrongException {
        return feedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(Integer feedbackId) throws ResourceNotFoundException, SomethingWentWrongException {
        Feedback feedback = getFeedbackById(feedbackId);
        feedbackRepository.delete(feedback);
    }
}

