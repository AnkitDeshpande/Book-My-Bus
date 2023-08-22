package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.masai.model.Route;
import com.masai.service.RouteService;
import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import java.util.List;

@RestController
public class RouteController {

	@Autowired
	private RouteService routeService;

	@GetMapping("/api/routes")
	public ResponseEntity<List<Route>> getAllRoutes() {
		List<Route> routes = routeService.getAllRoutes();
		return new ResponseEntity<>(routes, HttpStatus.OK);
	}

	@GetMapping("/api/routes/{id}")
	public ResponseEntity<Route> getRouteById(@PathVariable Integer id) throws ResourceNotFoundException {
		Route route = routeService.getRouteById(id);
		return new ResponseEntity<>(route, HttpStatus.OK);
	}

	@PostMapping("/api/routes")
	public ResponseEntity<Route> createRoute(@RequestBody Route route)
			throws ValidationException, SomethingWentWrongException {
		Route createdRoute = routeService.saveRoute(route);
		return new ResponseEntity<>(createdRoute, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/routes/{id}")
	public ResponseEntity<String> deleteRoute(@PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		routeService.deleteRoute(id);
		return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
	}

}