package com.masai.model;

import com.masai.enums.BookingStatus;
import com.masai.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_number", nullable = false, unique = true)
    private String bookingNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Column(name = "journey_date", nullable = false)
    private LocalDate journeyDate;

    @Column(name = "seat_count", nullable = false)
    private Integer seatCount;

    @Column(name = "total_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFare;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false)
    private BookingStatus bookingStatus = BookingStatus.CONFIRMED;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PAID;

    @Column(name = "passenger_name", nullable = false)
    private String passengerName;

    @Column(name = "passenger_phone", nullable = false)
    private String passengerPhone;
}
