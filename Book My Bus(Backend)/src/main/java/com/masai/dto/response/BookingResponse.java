package com.masai.dto.response;

import com.masai.enums.BookingStatus;
import com.masai.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class BookingResponse {

    private Long id;
    private String bookingNumber;
    private Long userId;
    private String username;
    private Long busId;
    private String busName;
    private String busNumber;
    private String routeSource;
    private String routeDestination;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalDate journeyDate;
    private Integer seatCount;
    private BigDecimal totalFare;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private String passengerName;
    private String passengerPhone;
    private LocalDateTime createdAt;
}
