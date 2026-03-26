package com.masai.controller;

import com.masai.dto.ApiResponse;
import com.masai.dto.PagedResponse;
import com.masai.dto.request.RouteRequest;
import com.masai.dto.response.RouteResponse;
import com.masai.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/routes")
@RequiredArgsConstructor
@Tag(name = "Routes", description = "Route management")
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create a new route (Admin only)")
    public ResponseEntity<ApiResponse<RouteResponse>> createRoute(@Valid @RequestBody RouteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Route created successfully", routeService.createRoute(request)));
    }

    @PutMapping("/{routeId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a route (Admin only)")
    public ResponseEntity<ApiResponse<RouteResponse>> updateRoute(@PathVariable Long routeId,
                                                                   @Valid @RequestBody RouteRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Route updated successfully", routeService.updateRoute(routeId, request)));
    }

    @DeleteMapping("/{routeId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Deactivate a route (Admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteRoute(@PathVariable Long routeId) {
        routeService.deleteRoute(routeId);
        return ResponseEntity.ok(ApiResponse.success("Route deactivated successfully", null));
    }

    @GetMapping("/{routeId}")
    @Operation(summary = "Get route by ID")
    public ResponseEntity<ApiResponse<RouteResponse>> getRouteById(@PathVariable Long routeId) {
        return ResponseEntity.ok(ApiResponse.success("Route fetched", routeService.getRouteById(routeId)));
    }

    @GetMapping
    @Operation(summary = "Get all routes (paginated)")
    public ResponseEntity<ApiResponse<PagedResponse<RouteResponse>>> getAllRoutes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Routes fetched", routeService.getAllRoutes(page, size)));
    }
}
