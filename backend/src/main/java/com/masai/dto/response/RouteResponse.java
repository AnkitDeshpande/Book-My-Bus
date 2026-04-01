package com.masai.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class RouteResponse {

    private Long id;
    private String source;
    private String destination;
    private Integer distanceKm;
    private BigDecimal baseFare;
    private boolean active;
    private LocalDateTime createdAt;
}
