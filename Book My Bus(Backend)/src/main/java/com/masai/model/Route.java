package com.masai.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer routeId;

	private String routeFrom;

	private String routeTo;

	private Integer distance;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
	private Set<Bus> buslist = new HashSet<Bus>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		return Objects.equals(routeFrom, other.routeFrom) && Objects.equals(routeTo, other.routeTo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(routeFrom, routeTo);
	}

}
