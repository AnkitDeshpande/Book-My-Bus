package com.masai.service;

import java.util.List;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route getRouteById(Integer routeId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route saveRoute(Route route) throws ValidationException, SomethingWentWrongException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRoute(Integer routeId) throws ResourceNotFoundException, SomethingWentWrongException {
		// TODO Auto-generated method stub

	}

}
