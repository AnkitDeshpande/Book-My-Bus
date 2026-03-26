package com.masai.service.impl;

import com.masai.dto.response.DashboardStats;
import com.masai.dto.response.RevenueReport;
import com.masai.enums.BookingStatus;
import com.masai.repository.BookingRepository;
import com.masai.repository.BusRepository;
import com.masai.repository.RouteRepository;
import com.masai.repository.UserRepository;
import com.masai.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final BookingRepository bookingRepository;

    @Override
    public DashboardStats getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalBuses = busRepository.count();
        long totalRoutes = routeRepository.count();
        long totalBookings = bookingRepository.count();
        long confirmed = bookingRepository.countByBookingStatus(BookingStatus.CONFIRMED);
        long cancelled = bookingRepository.countByBookingStatus(BookingStatus.CANCELLED);

        LocalDateTime monthStart = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime now = LocalDateTime.now();
        BigDecimal revenue = bookingRepository.sumTotalFareByStatusAndCreatedAtBetween(
                BookingStatus.CONFIRMED, monthStart, now);

        log.info("Dashboard stats fetched");
        return new DashboardStats(totalUsers, totalBuses, totalRoutes, totalBookings, confirmed, cancelled, revenue);
    }

    @Override
    public RevenueReport getRevenueReport(LocalDateTime from, LocalDateTime to) {
        BigDecimal revenue = bookingRepository.sumTotalFareByStatusAndCreatedAtBetween(
                BookingStatus.CONFIRMED, from, to);
        long totalBookings = bookingRepository.countByCreatedAtBetween(from, to);
        long confirmed = bookingRepository.countByBookingStatusAndCreatedAtBetween(BookingStatus.CONFIRMED, from, to);
        return new RevenueReport(from, to, revenue, totalBookings, confirmed);
    }
}
