package com.masai.dto.response;

import com.masai.enums.BusType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class BusResponse {

    private Long id;
    private String busNumber;
    private String busName;
    private String driverName;
    private BusType busType;
    private Integer totalSeats;
    private Integer availableSeats;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private boolean active;
    private Long routeId;
    private String routeSource;
    private String routeDestination;
    private LocalDateTime createdAt;
}
