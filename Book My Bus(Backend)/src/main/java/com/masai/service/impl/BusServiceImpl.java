package com.masai.service.impl;

import com.masai.dto.PagedResponse;
import com.masai.dto.request.BusRequest;
import com.masai.dto.response.BusResponse;
import com.masai.enums.BusType;
import com.masai.exception.DuplicateResourceException;
import com.masai.exception.ResourceNotFoundException;
import com.masai.model.Bus;
import com.masai.model.Route;
import com.masai.repository.BusRepository;
import com.masai.repository.RouteRepository;
import com.masai.service.BusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final RouteRepository routeRepository;

    @Override
    @Transactional
    public BusResponse createBus(BusRequest request) {
        if (busRepository.existsByBusNumber(request.getBusNumber())) {
            throw new DuplicateResourceException("Bus number already exists: " + request.getBusNumber());
        }
        Route route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route", "id", request.getRouteId()));

        Bus bus = new Bus();
        bus.setBusNumber(request.getBusNumber());
        bus.setBusName(request.getBusName());
        bus.setDriverName(request.getDriverName());
        bus.setBusType(request.getBusType());
        bus.setTotalSeats(request.getTotalSeats());
        bus.setAvailableSeats(request.getTotalSeats());
        bus.setDepartureTime(request.getDepartureTime());
        bus.setArrivalTime(request.getArrivalTime());
        bus.setRoute(route);

        Bus saved = busRepository.save(bus);
        log.info("Bus created: {}", saved.getBusNumber());
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public BusResponse updateBus(Long busId, BusRequest request) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "id", busId));
        Route route = routeRepository.findById(request.getRouteId())
                .orElseThrow(() -> new ResourceNotFoundException("Route", "id", request.getRouteId()));

        bus.setBusName(request.getBusName());
        bus.setDriverName(request.getDriverName());
        bus.setBusType(request.getBusType());
        bus.setTotalSeats(request.getTotalSeats());
        bus.setDepartureTime(request.getDepartureTime());
        bus.setArrivalTime(request.getArrivalTime());
        bus.setRoute(route);

        return mapToResponse(busRepository.save(bus));
    }

    @Override
    @Transactional(readOnly = true)
    public BusResponse getBusById(Long busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "id", busId));
        return mapToResponse(bus);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<BusResponse> getAllBuses(int page, int size) {
        Page<Bus> busPage = busRepository.findAll(PageRequest.of(page, size, Sort.by("busName")));
        return new PagedResponse<>(
                busPage.getContent().stream().map(this::mapToResponse).toList(),
                busPage.getNumber(),
                busPage.getSize(),
                busPage.getTotalElements(),
                busPage.getTotalPages(),
                busPage.isLast()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusResponse> searchBuses(String source, String destination, int seatCount, BusType busType) {
        List<Bus> buses = busType != null
                ? busRepository.findAvailableBusesByType(source, destination, seatCount, busType)
                : busRepository.findAvailableBuses(source, destination, seatCount);
        return buses.stream().map(this::mapToResponse).toList();
    }

    @Override
    @Transactional
    public void deleteBus(Long busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new ResourceNotFoundException("Bus", "id", busId));
        bus.setActive(false);
        busRepository.save(bus);
        log.info("Bus deactivated: {}", busId);
    }

    private BusResponse mapToResponse(Bus bus) {
        BusResponse dto = new BusResponse();
        dto.setId(bus.getId());
        dto.setBusNumber(bus.getBusNumber());
        dto.setBusName(bus.getBusName());
        dto.setDriverName(bus.getDriverName());
        dto.setBusType(bus.getBusType());
        dto.setTotalSeats(bus.getTotalSeats());
        dto.setAvailableSeats(bus.getAvailableSeats());
        dto.setDepartureTime(bus.getDepartureTime());
        dto.setArrivalTime(bus.getArrivalTime());
        dto.setActive(bus.isActive());
        dto.setCreatedAt(bus.getCreatedAt());
        if (bus.getRoute() != null) {
            dto.setRouteId(bus.getRoute().getId());
            dto.setRouteSource(bus.getRoute().getSource());
            dto.setRouteDestination(bus.getRoute().getDestination());
            dto.setBaseFare(bus.getRoute().getBaseFare());
        }
        return dto;
    }
}
