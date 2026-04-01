package com.masai.repository;

import com.masai.enums.BookingStatus;
import com.masai.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findByUser_Id(Long userId, Pageable pageable);

    List<Booking> findByJourneyDate(LocalDate journeyDate);

    long countByBookingStatus(BookingStatus status);

    Optional<Booking> findByBookingNumber(String bookingNumber);

    long countByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    long countByBookingStatusAndCreatedAtBetween(BookingStatus status, LocalDateTime from, LocalDateTime to);

    @Query("SELECT COALESCE(SUM(b.totalFare), 0) FROM Booking b " +
           "WHERE b.bookingStatus = :status " +
           "AND b.createdAt BETWEEN :from AND :to")
    BigDecimal sumTotalFareByStatusAndCreatedAtBetween(
            @Param("status") BookingStatus status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);
}
