package com.masai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "routes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"source", "destination"})
})
@Getter
@Setter
@NoArgsConstructor
public class Route extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @Column(name = "distance_km", nullable = false)
    private Integer distanceKm;

    @Column(name = "base_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseFare;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY)
    private List<Bus> buses = new ArrayList<>();
}
