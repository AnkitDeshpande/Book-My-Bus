package com.masai.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Route;
import com.masai.service.RouteService;

import jakarta.validation.Valid;

@CrossOrigin
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
	public ResponseEntity<Route> getRouteById(@Valid @PathVariable Integer id) throws ResourceNotFoundException {
		Route route = routeService.getRouteById(id);
		return new ResponseEntity<>(route, HttpStatus.OK);
	}

	@PostMapping("/api/routes")
	public ResponseEntity<Route> createRoute(@Valid @RequestBody Route route)
			throws ValidationException, SomethingWentWrongException {
		Route createdRoute = routeService.saveRoute(route);
		return new ResponseEntity<>(createdRoute, HttpStatus.CREATED);
	}

	@DeleteMapping("/api/routes/{id}")
	public ResponseEntity<String> deleteRoute(@Valid @PathVariable Integer id)
			throws ResourceNotFoundException, SomethingWentWrongException {
		routeService.deleteRoute(id);
		return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
	}
}
