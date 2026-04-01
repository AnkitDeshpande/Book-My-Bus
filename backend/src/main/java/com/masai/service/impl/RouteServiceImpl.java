package com.masai.service.impl;

import com.masai.dto.PagedResponse;
import com.masai.dto.request.RouteRequest;
import com.masai.dto.response.RouteResponse;
import com.masai.exception.DuplicateResourceException;
import com.masai.exception.ResourceNotFoundException;
import com.masai.model.Route;
import com.masai.repository.RouteRepository;
import com.masai.service.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    @Override
    @Transactional
    public RouteResponse createRoute(RouteRequest request) {
        if (routeRepository.existsBySourceIgnoreCaseAndDestinationIgnoreCase(request.getSource(), request.getDestination())) {
            throw new DuplicateResourceException("Route already exists: " + request.getSource() + " → " + request.getDestination());
        }
        Route route = new Route();
        route.setSource(request.getSource());
        route.setDestination(request.getDestination());
        route.setDistanceKm(request.getDistanceKm());
        route.setBaseFare(request.getBaseFare());
        Route saved = routeRepository.save(route);
        log.info("Route created: {} → {}", saved.getSource(), saved.getDestination());
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public RouteResponse updateRoute(Long routeId, RouteRequest request) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route", "id", routeId));
        route.setSource(request.getSource());
        route.setDestination(request.getDestination());
        route.setDistanceKm(request.getDistanceKm());
        route.setBaseFare(request.getBaseFare());
        return mapToResponse(routeRepository.save(route));
    }

    @Override
    public RouteResponse getRouteById(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route", "id", routeId));
        return mapToResponse(route);
    }

    @Override
    public PagedResponse<RouteResponse> getAllRoutes(int page, int size) {
        Page<Route> routePage = routeRepository.findAll(PageRequest.of(page, size, Sort.by("source")));
        return new PagedResponse<>(
                routePage.getContent().stream().map(this::mapToResponse).toList(),
                routePage.getNumber(),
                routePage.getSize(),
                routePage.getTotalElements(),
                routePage.getTotalPages(),
                routePage.isLast()
        );
    }

    @Override
    @Transactional
    public void deleteRoute(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new ResourceNotFoundException("Route", "id", routeId));
        route.setActive(false);
        routeRepository.save(route);
        log.info("Route deactivated: {}", routeId);
    }

    private RouteResponse mapToResponse(Route route) {
        RouteResponse dto = new RouteResponse();
        dto.setId(route.getId());
        dto.setSource(route.getSource());
        dto.setDestination(route.getDestination());
        dto.setDistanceKm(route.getDistanceKm());
        dto.setBaseFare(route.getBaseFare());
        dto.setActive(route.isActive());
        dto.setCreatedAt(route.getCreatedAt());
        return dto;
    }
}
