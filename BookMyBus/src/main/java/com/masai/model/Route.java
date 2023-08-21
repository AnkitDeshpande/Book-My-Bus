package com.masai.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer routeId;

	@NotBlank(message = "source cannot be empty.")
	private String routeFrom;

	@NotBlank(message = "Destination cannot be empty.")
	private String routeTo;

	private Integer distance;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
	private List<Bus> buslist;

	public Route(Integer routeId, String routeFrom, String routeTo, Integer distance, List<Bus> buslist) {
		super();
		this.routeId = routeId;
		this.routeFrom = routeFrom;
		this.routeTo = routeTo;
		this.distance = distance;
		this.buslist = buslist;
	}

}
