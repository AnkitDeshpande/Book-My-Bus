package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.masai.model.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer> {
    
}
