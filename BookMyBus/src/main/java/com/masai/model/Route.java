package com.masai.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer routeId;

    @NotBlank(message = "Source is mandatory")
    private String routeFrom;

    @NotBlank(message = "Destination is mandatory")
    private String routeTo;

    @NotNull(message = "Distance is mandatory")
    @Min(value = 0, message = "Distance cannot be negative")
    private Integer distance;

    private boolean deleted = false;

    @OneToMany( mappedBy = "route")
    private List<Bus> busList;

	public Route(@NotBlank(message = "Source is mandatory") String routeFrom,
			@NotBlank(message = "Destination is mandatory") String routeTo,
			@NotNull(message = "Distance is mandatory") @Min(value = 0, message = "Distance cannot be negative") Integer distance,
			List<Bus> busList) {
		super();
		this.routeFrom = routeFrom;
		this.routeTo = routeTo;
		this.distance = distance;
		this.busList = busList;
	}

    
}
