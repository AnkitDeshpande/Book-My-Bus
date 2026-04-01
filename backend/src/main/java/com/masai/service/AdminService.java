package com.masai.service;

import com.masai.dto.response.DashboardStats;
import com.masai.dto.response.RevenueReport;

import java.time.LocalDateTime;

public interface AdminService {

    DashboardStats getDashboardStats();

    RevenueReport getRevenueReport(LocalDateTime from, LocalDateTime to);
}
