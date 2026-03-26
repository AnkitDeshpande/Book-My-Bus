package com.masai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RevenueReport {

    private LocalDateTime from;
    private LocalDateTime to;
    private BigDecimal totalRevenue;
    private long totalBookings;
    private long confirmedBookings;
}
