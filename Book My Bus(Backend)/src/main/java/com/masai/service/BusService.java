package com.masai.service;

import com.masai.dto.PagedResponse;
import com.masai.dto.request.BusRequest;
import com.masai.dto.response.BusResponse;

import java.util.List;

public interface BusService {

    BusResponse createBus(BusRequest request);

    BusResponse updateBus(Long busId, BusRequest request);

    BusResponse getBusById(Long busId);

    PagedResponse<BusResponse> getAllBuses(int page, int size);

    List<BusResponse> searchBuses(String source, String destination, int seatCount);

    void deleteBus(Long busId);
}
