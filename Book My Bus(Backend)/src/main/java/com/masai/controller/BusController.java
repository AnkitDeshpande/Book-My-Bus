package com.masai.controller;

import com.masai.dto.ApiResponse;
import com.masai.dto.PagedResponse;
import com.masai.dto.request.BusRequest;
import com.masai.dto.response.BusResponse;
import com.masai.enums.BusType;
import com.masai.service.BusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buses")
@RequiredArgsConstructor
@Tag(name = "Buses", description = "Bus management and search")
public class BusController {

    private final BusService busService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Add a new bus (Admin only)")
    public ResponseEntity<ApiResponse<BusResponse>> createBus(@Valid @RequestBody BusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Bus created successfully", busService.createBus(request)));
    }

    @PutMapping("/{busId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Update a bus (Admin only)")
    public ResponseEntity<ApiResponse<BusResponse>> updateBus(@PathVariable Long busId,
                                                               @Valid @RequestBody BusRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Bus updated successfully", busService.updateBus(busId, request)));
    }

    @DeleteMapping("/{busId}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Deactivate a bus (Admin only)")
    public ResponseEntity<ApiResponse<Void>> deleteBus(@PathVariable Long busId) {
        busService.deleteBus(busId);
        return ResponseEntity.ok(ApiResponse.success("Bus deactivated successfully", null));
    }

    @GetMapping("/{busId}")
    @Operation(summary = "Get bus by ID")
    public ResponseEntity<ApiResponse<BusResponse>> getBusById(@PathVariable Long busId) {
        return ResponseEntity.ok(ApiResponse.success("Bus fetched", busService.getBusById(busId)));
    }

    @GetMapping
    @Operation(summary = "Get all buses (paginated)")
    public ResponseEntity<ApiResponse<PagedResponse<BusResponse>>> getAllBuses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Buses fetched", busService.getAllBuses(page, size)));
    }

    @GetMapping("/search")
    @Operation(summary = "Search available buses by source, destination, seat count and optional bus type")
    public ResponseEntity<ApiResponse<List<BusResponse>>> searchBuses(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam(defaultValue = "1") int seatCount,
            @RequestParam(required = false) BusType busType) {
        return ResponseEntity.ok(ApiResponse.success("Buses found", busService.searchBuses(source, destination, seatCount, busType)));
    }
}
