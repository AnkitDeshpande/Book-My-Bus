package com.masai.repository;

import com.masai.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    Optional<Route> findBySourceIgnoreCaseAndDestinationIgnoreCase(String source, String destination);

    boolean existsBySourceIgnoreCaseAndDestinationIgnoreCase(String source, String destination);

    long countByActiveTrue();
}
