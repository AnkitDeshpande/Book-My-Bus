package com.masai.service.impl;

import com.masai.dto.PagedResponse;
import com.masai.dto.request.BookingRequest;
import com.masai.dto.response.BookingResponse;
import com.masai.enums.BookingStatus;
import com.masai.enums.PaymentStatus;
import com.masai.exception.BadRequestException;
import com.masai.exception.BusinessException;
import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.UnauthorizedException;
import com.masai.model.Booking;
import com.masai.model.Bus;
import com.masai.model.User;
import com.masai.repository.BookingRepository;
import com.masai.repository.BusRepository;
import com.masai.repository.UserRepository;
import com.masai.security.SecurityUtils;
import com.masai.service.BookingService;
import com.masai.service.EmailService;
import com.masai.util.BookingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BusRepository busRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Bus bus = busRepository.findById(request.getBusId())
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "id", request.getBusId()));

        if (!bus.isActive()) {
            throw new BadRequestException("Bus is not active");
        }
        if (bus.getAvailableSeats() < request.getSeatCount()) {
            throw new BusinessException("Not enough seats available. Available: " + bus.getAvailableSeats());
        }

        BigDecimal totalFare = bus.getRoute().getBaseFare()
                .multiply(BigDecimal.valueOf(request.getSeatCount()));

        bus.setAvailableSeats(bus.getAvailableSeats() - request.getSeatCount());
        busRepository.save(bus);

        Booking booking = new Booking();
        booking.setBookingNumber(BookingUtils.generateBookingNumber());
        booking.setUser(user);
        booking.setBus(bus);
        booking.setJourneyDate(request.getJourneyDate());
        booking.setSeatCount(request.getSeatCount());
        booking.setTotalFare(totalFare);
        booking.setPassengerName(request.getPassengerName());
        booking.setPassengerPhone(request.getPassengerPhone());

        Booking saved = bookingRepository.save(booking);
        log.info("Booking created: {} for user: {}", saved.getBookingNumber(), userId);

        try {
            emailService.sendBookingConfirmationEmail(
                    user.getEmail(),
                    user.getFirstName(),
                    saved.getBookingNumber(),
                    bus.getBusName(),
                    bus.getRoute().getSource(),
                    bus.getRoute().getDestination(),
                    saved.getJourneyDate().toString(),
                    saved.getSeatCount(),
                    saved.getTotalFare().toPlainString()
            );
        } catch (Exception e) {
            log.warn("Booking confirmation email failed for {}: {}", saved.getBookingNumber(), e.getMessage());
        }

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBookingById(Long bookingId) {
        Long userId = SecurityUtils.getCurrentUserId();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

        if (!SecurityUtils.isAdmin() && !booking.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Access denied to this booking");
        }
        return mapToResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<BookingResponse> getMyBookings(int page, int size) {
        Long userId = SecurityUtils.getCurrentUserId();
        Page<Booking> bookingPage = bookingRepository.findByUser_Id(
                userId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return toPagedResponse(bookingPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<BookingResponse> getAllBookings(int page, int size) {
        Page<Booking> bookingPage = bookingRepository.findAll(
                PageRequest.of(page, size, Sort.by("createdAt").descending()));
        return toPagedResponse(bookingPage);
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long bookingId) {
        Long userId = SecurityUtils.getCurrentUserId();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

        if (!SecurityUtils.isAdmin() && !booking.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("Access denied to this booking");
        }
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking is already cancelled");
        }
        if (booking.getJourneyDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot cancel a booking after the journey date has passed");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.setPaymentStatus(PaymentStatus.REFUNDED);

        Bus bus = booking.getBus();
        bus.setAvailableSeats(bus.getAvailableSeats() + booking.getSeatCount());
        busRepository.save(bus);

        Booking updated = bookingRepository.save(booking);
        log.info("Booking cancelled: {}", updated.getBookingNumber());

        try {
            User user = booking.getUser();
            emailService.sendBookingCancellationEmail(
                    user.getEmail(),
                    user.getFirstName(),
                    updated.getBookingNumber(),
                    bus.getBusName(),
                    updated.getJourneyDate().toString(),
                    updated.getTotalFare().toPlainString()
            );
        } catch (Exception e) {
            log.warn("Booking cancellation email failed for {}: {}", updated.getBookingNumber(), e.getMessage());
        }

        return mapToResponse(updated);
    }

    private PagedResponse<BookingResponse> toPagedResponse(Page<Booking> page) {
        return new PagedResponse<>(
                page.getContent().stream().map(this::mapToResponse).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    private BookingResponse mapToResponse(Booking b) {
        BookingResponse dto = new BookingResponse();
        dto.setId(b.getId());
        dto.setBookingNumber(b.getBookingNumber());
        dto.setUserId(b.getUser().getId());
        dto.setUsername(b.getUser().getUsername());
        dto.setBusId(b.getBus().getId());
        dto.setBusName(b.getBus().getBusName());
        dto.setBusNumber(b.getBus().getBusNumber());
        dto.setDepartureTime(b.getBus().getDepartureTime());
        dto.setArrivalTime(b.getBus().getArrivalTime());
        if (b.getBus().getRoute() != null) {
            dto.setRouteSource(b.getBus().getRoute().getSource());
            dto.setRouteDestination(b.getBus().getRoute().getDestination());
        }
        dto.setJourneyDate(b.getJourneyDate());
        dto.setSeatCount(b.getSeatCount());
        dto.setTotalFare(b.getTotalFare());
        dto.setBookingStatus(b.getBookingStatus());
        dto.setPaymentStatus(b.getPaymentStatus());
        dto.setPassengerName(b.getPassengerName());
        dto.setPassengerPhone(b.getPassengerPhone());
        dto.setCreatedAt(b.getCreatedAt());
        return dto;
    }
}
