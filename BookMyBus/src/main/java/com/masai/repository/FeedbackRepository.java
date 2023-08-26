package com.masai.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.masai.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

	@Query("SELECT f FROM Feedback f WHERE f.bus.id = :busId")
    List<Feedback> findByBus_Id(@Param("busId") Integer busId);
    
    @Query("SELECT f FROM Feedback f WHERE f.user.id = :userId")
    List<Feedback> findByUser_Id(@Param("userId") Integer userId);

	List<Feedback> findByOverallRatingGreaterThanEqual(Integer rating);

	List<Feedback> findByDeletedTrue();

	List<Feedback> findByDeletedFalse();

	List<Feedback> findByOverallRatingLessThanEqual(Integer rating);

}
