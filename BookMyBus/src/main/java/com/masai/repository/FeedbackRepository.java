package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

}
