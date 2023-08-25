package com.masai.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Bus;

/**
 * Service interface for managing bus-related operations.
 */
public interface BusService {

    /**
     * Retrieves a list of all buses.
     *
     * @return A list containing all available buses.
     */
    List<Bus> getAllBuses();

    /**
     * Retrieves a bus by its unique identifier.
     *
     * @param busId The ID of the bus to retrieve.
     * @return The retrieved bus.
     * @throws ResourceNotFoundException If the requested bus is not found.
     */
    Bus getBusById(Integer busId) throws ResourceNotFoundException;

    /**
     * Saves a new bus to the system.
     *
     * @param bus The bus object to be saved.
     * @return The saved bus.
     * @throws ValidationException        If the bus data is not valid.
     * @throws SomethingWentWrongException If an unexpected error occurs.
     */
    Bus saveBus(Bus bus) throws ValidationException, SomethingWentWrongException;

    /**
     * Deletes a bus by its unique identifier.
     *
     * @param busId The ID of the bus to be deleted.
     * @throws ResourceNotFoundException If the requested bus is not found.
     * @throws SomethingWentWrongException If an unexpected error occurs.
     */
    void deleteBus(Integer busId) throws ResourceNotFoundException, SomethingWentWrongException;

    /**
     * Updates an existing bus with new data.
     *
     * @param busId       The ID of the bus to be updated.
     * @param updatedBus  The updated bus data.
     * @return The updated bus.
     * @throws ResourceNotFoundException  If the requested bus is not found.
     * @throws ValidationException        If the updated bus data is not valid.
     * @throws SomethingWentWrongException If an unexpected error occurs.
     */
    Bus updateBus(Integer busId, Bus updatedBus)
            throws ResourceNotFoundException, ValidationException, SomethingWentWrongException;

    /**
     * Retrieves a paginated list of all buses.
     *
     * @param pageable The pagination information.
     * @return A page containing a subset of all available buses.
     * @throws SomethingWentWrongException If an unexpected error occurs.
     */
    Page<Bus> getAllBuses(Pageable pageable) throws SomethingWentWrongException;

    /**
     * Retrieves a list of buses that operate on a specific route.
     *
     * @param routeId The ID of the route.
     * @return A list of buses operating on the specified route.
     * @throws SomethingWentWrongException If an unexpected error occurs.
     */
    List<Bus> getBusesByRoute(Integer routeId) throws SomethingWentWrongException;

    /**
     * Retrieves a list of available buses.
     *
     * @return A list of buses with available seats.
     * @throws SomethingWentWrongException If an unexpected error occurs.
     */
    List<Bus> getAvailableBuses() throws SomethingWentWrongException;

    /**
     * Retrieves a list of buses with available seats.
     *
     * @return A list of buses with available seats.
     * @throws SomethingWentWrongException If an unexpected error occurs.
     */
    List<Bus> getBusesWithAvailableSeats() throws SomethingWentWrongException;

    /**
     * Retrieves a list of buses within a specified arrival time range.
     *
     * @param startTime The start time of the arrival time range.
     * @param endTime   The end time of the arrival time range.
     * @return A list of buses arriving within the specified time range.
     * @throws SomethingWentWrongException If an unexpected error occurs.
     */
    List<Bus> getBusesByArrivalTimeRange(LocalTime startTime, LocalTime endTime) throws SomethingWentWrongException;

    /**
     * Retrieves a list of buses within a specified departure time range.
     *
     * @param startTime The start time of the departure time range.
     * @param endTime   The end time of the departure time range.
     * @return A list of buses departing within the specified time range.
     * @throws SomethingWentWrongException If an unexpected error occurs.
     */
    List<Bus> getBusesByDepartureTimeRange(LocalTime startTime, LocalTime endTime) throws SomethingWentWrongException;
}

