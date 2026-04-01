package com.masai.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class DashboardStats {

    private long totalUsers;
    private long totalBuses;
    private long totalRoutes;
    private long totalBookings;
    private long confirmedBookings;
    private long cancelledBookings;
    private BigDecimal totalRevenue;
}
