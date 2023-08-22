package com.masai.service;

import java.util.List;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Route;

public interface RouteService {
	List<Route> getAllRoutes();

	Route getRouteById(Integer routeId) throws ResourceNotFoundException;

	Route saveRoute(Route route) throws ValidationException, SomethingWentWrongException;

	void deleteRoute(Integer routeId) throws ResourceNotFoundException, SomethingWentWrongException;
}
