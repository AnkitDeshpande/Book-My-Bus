package com.masai.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "feedbacks", uniqueConstraints = {
        @UniqueConstraint(columnNames = "booking_id")
})
@Getter
@Setter
@NoArgsConstructor
public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @Column(name = "overall_rating", nullable = false)
    private Integer overallRating;

    @Column(name = "driver_rating", nullable = false)
    private Integer driverRating;

    @Column(name = "service_rating", nullable = false)
    private Integer serviceRating;

    @Column(columnDefinition = "TEXT")
    private String comment;
}
