package com.masai.repository;

import com.masai.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByBooking_Bus_Id(Long busId);

    List<Feedback> findByBooking_User_Id(Long userId);

    boolean existsByBooking_Id(Long bookingId);
}
