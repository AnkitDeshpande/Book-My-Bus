package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.masai.model.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {
	
}
