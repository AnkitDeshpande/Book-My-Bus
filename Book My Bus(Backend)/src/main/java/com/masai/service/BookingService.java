package com.masai.service;

import com.masai.dto.PagedResponse;
import com.masai.dto.request.BookingRequest;
import com.masai.dto.response.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

    BookingResponse getBookingById(Long bookingId);

    PagedResponse<BookingResponse> getMyBookings(int page, int size);

    PagedResponse<BookingResponse> getAllBookings(int page, int size);

    BookingResponse cancelBooking(Long bookingId);
}
