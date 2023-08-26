package com.masai.service;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
		if(routeRepository.findAll().isEmpty()) {;
			throw new ResourceNotFoundException("No Routes currently available ");
		}
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
		if(route==null) {
			throw new ResourceNotFoundException("Not Found the route with the RouteId : "+routeId);
		}
		
	}

	@Override
	public Route updateRoute(Route route) {
		// TODO Auto-generated method stub
		Route route1 = getRouteById(route.getRouteId());
		if(route1==null) {
			throw new ResourceNotFoundException("Not Found the route with the RouteId : "+route.getRouteId());
		}
		return routeRepository.save(route);
	}

	@Override
	public List<Route> paginationAndSort(int page, int limit) {
		// TODO Auto-generated method stub
		Pageable of = PageRequest.of(page, limit);
		Page<Route> findAll = routeRepository.findAll(of);
		System.out.println(findAll);
		List<Route> content = findAll.getContent();
		if(content.isEmpty()){
			throw new ResourceNotFoundException("Not Found Routes within "+page+" "+limit);
		}
		return content ;
	}

	@Override
	public List<Route> sort(String field,String sort) {
		// TODO Auto-generated method stub
		Sort by = null;
		if(sort.equalsIgnoreCase("ASC")) {
			by=Sort.by(field).ascending();	
		}else {
			by=Sort.by(field).descending();	
		
		}
		List<Route> findAll = routeRepository.findAll(by);
		return findAll;
	}
	
	
}
