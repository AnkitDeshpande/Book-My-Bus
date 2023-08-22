package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Route;
import com.masai.repository.RouteRepository;

@Service
public class RouteServiceImpl implements RouteService {

	@Autowired
	private RouteRepository routeRepository;

	@Override
	public List<Route> getAllRoutes() {
		return routeRepository.findAll();
	}

	@Override
	public Route getRouteById(Integer routeId) throws ResourceNotFoundException {
		Optional<Route> optionalRoute = routeRepository.findById(routeId);
		return optionalRoute.orElseThrow(() -> new ResourceNotFoundException("Route not found with ID: " + routeId));
	}

	@Override
	public Route saveRoute(Route route) throws ValidationException, SomethingWentWrongException {

		return routeRepository.save(route);
	}

	@Override
	public void deleteRoute(Integer routeId) throws ResourceNotFoundException, SomethingWentWrongException {
		Route route = getRouteById(routeId);
		routeRepository.delete(route);
	}

}
