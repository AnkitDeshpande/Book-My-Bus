package com.masai.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RouteRequest {

    @NotBlank(message = "Source is required")
    private String source;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Distance is required")
    @Min(value = 1, message = "Distance must be at least 1 km")
    private Integer distanceKm;

    @NotNull(message = "Base fare is required")
    @DecimalMin(value = "0.01", message = "Base fare must be greater than 0")
    private BigDecimal baseFare;
}
