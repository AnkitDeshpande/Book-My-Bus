package com.masai.service;

import com.masai.dto.PagedResponse;
import com.masai.dto.request.RouteRequest;
import com.masai.dto.response.RouteResponse;

public interface RouteService {

    RouteResponse createRoute(RouteRequest request);

    RouteResponse updateRoute(Long routeId, RouteRequest request);

    RouteResponse getRouteById(Long routeId);

    PagedResponse<RouteResponse> getAllRoutes(int page, int size);

    void deleteRoute(Long routeId);
}
