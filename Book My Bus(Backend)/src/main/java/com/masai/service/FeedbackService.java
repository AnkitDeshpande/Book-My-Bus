package com.masai.service;

import com.masai.dto.request.FeedbackRequest;
import com.masai.dto.response.FeedbackResponse;

import java.util.List;

public interface FeedbackService {

    FeedbackResponse submitFeedback(FeedbackRequest request);

    List<FeedbackResponse> getMyFeedbacks();

    List<FeedbackResponse> getFeedbacksByBus(Long busId);
}
