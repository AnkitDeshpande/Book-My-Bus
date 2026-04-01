package com.masai.repository;

import com.masai.enums.BusType;
import com.masai.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    @Query("SELECT b FROM Bus b JOIN FETCH b.route r " +
           "WHERE LOWER(r.source) LIKE LOWER(CONCAT('%', :source, '%')) " +
           "AND LOWER(r.destination) LIKE LOWER(CONCAT('%', :destination, '%')) " +
           "AND b.availableSeats >= :seatCount " +
           "AND b.active = true")
    List<Bus> findAvailableBuses(@Param("source") String source,
                                 @Param("destination") String destination,
                                 @Param("seatCount") int seatCount);

    @Query("SELECT b FROM Bus b JOIN FETCH b.route r " +
           "WHERE LOWER(r.source) LIKE LOWER(CONCAT('%', :source, '%')) " +
           "AND LOWER(r.destination) LIKE LOWER(CONCAT('%', :destination, '%')) " +
           "AND b.availableSeats >= :seatCount " +
           "AND b.active = true " +
           "AND b.busType = :busType")
    List<Bus> findAvailableBusesByType(@Param("source") String source,
                                       @Param("destination") String destination,
                                       @Param("seatCount") int seatCount,
                                       @Param("busType") BusType busType);

    List<Bus> findByBusType(BusType busType);

    boolean existsByBusNumber(String busNumber);

    long countByActiveTrue();
}
