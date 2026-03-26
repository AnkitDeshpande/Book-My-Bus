package com.masai.service.impl;

import com.masai.dto.request.FeedbackRequest;
import com.masai.dto.response.FeedbackResponse;
import com.masai.enums.BookingStatus;
import com.masai.exception.BadRequestException;
import com.masai.exception.BusinessException;
import com.masai.exception.DuplicateResourceException;
import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.UnauthorizedException;
import com.masai.model.Booking;
import com.masai.model.Feedback;
import com.masai.repository.BookingRepository;
import com.masai.repository.FeedbackRepository;
import com.masai.security.SecurityUtils;
import com.masai.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public FeedbackResponse submitFeedback(FeedbackRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", request.getBookingId()));

        if (!booking.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You can only leave feedback for your own bookings");
        }
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Cannot submit feedback for a cancelled booking");
        }
        if (booking.getJourneyDate().isAfter(LocalDate.now())) {
            throw new BusinessException("Cannot submit feedback before the journey date");
        }
        if (feedbackRepository.existsByBooking_Id(booking.getId())) {
            throw new DuplicateResourceException("Feedback already submitted for this booking");
        }

        Feedback feedback = new Feedback();
        feedback.setBooking(booking);
        feedback.setOverallRating(request.getOverallRating());
        feedback.setDriverRating(request.getDriverRating());
        feedback.setServiceRating(request.getServiceRating());
        feedback.setComment(request.getComment());

        Feedback saved = feedbackRepository.save(feedback);
        log.info("Feedback submitted for booking: {}", booking.getBookingNumber());
        return mapToResponse(saved);
    }

    @Override
    public List<FeedbackResponse> getMyFeedbacks() {
        Long userId = SecurityUtils.getCurrentUserId();
        return feedbackRepository.findByBooking_User_Id(userId)
                .stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<FeedbackResponse> getFeedbacksByBus(Long busId) {
        return feedbackRepository.findByBooking_Bus_Id(busId)
                .stream().map(this::mapToResponse).toList();
    }

    private FeedbackResponse mapToResponse(Feedback f) {
        FeedbackResponse dto = new FeedbackResponse();
        dto.setId(f.getId());
        dto.setBookingId(f.getBooking().getId());
        dto.setBookingNumber(f.getBooking().getBookingNumber());
        dto.setBusId(f.getBooking().getBus().getId());
        dto.setBusName(f.getBooking().getBus().getBusName());
        dto.setUserId(f.getBooking().getUser().getId());
        dto.setUsername(f.getBooking().getUser().getUsername());
        dto.setOverallRating(f.getOverallRating());
        dto.setDriverRating(f.getDriverRating());
        dto.setServiceRating(f.getServiceRating());
        dto.setComment(f.getComment());
        dto.setCreatedAt(f.getCreatedAt());
        return dto;
    }
}
