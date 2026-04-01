package com.masai.model;

import com.masai.enums.BusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "buses")
@Getter
@Setter
@NoArgsConstructor
public class Bus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bus_number", nullable = false, unique = true)
    private String busNumber;

    @Column(name = "bus_name", nullable = false)
    private String busName;

    @Column(name = "driver_name", nullable = false)
    private String driverName;

    @Enumerated(EnumType.STRING)
    @Column(name = "bus_type", nullable = false)
    private BusType busType;

    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalTime arrivalTime;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Version
    private Long version;
}
