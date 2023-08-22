package com.masai.service;

import java.util.List;

import com.masai.exception.ResourceNotFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.ValidationException;
import com.masai.model.Bus;

public interface BusService {
    List<Bus> getAllBuses();
    Bus getBusById(Integer busId) throws ResourceNotFoundException;
    Bus saveBus(Bus bus) throws ValidationException, SomethingWentWrongException;
    void deleteBus(Integer busId) throws ResourceNotFoundException, SomethingWentWrongException;
    
}
