package com.example.session16.service.busTrip;

import com.example.session16.model.BusTrip;

import java.util.List;

public interface BusTripService {
    List<BusTrip> getAllBusTrips(int page_size, int current_page);
    int totalBusTrips();
    List<BusTrip> searchBusTrips(String type, String keyword, int page_size, int current_page);
    int totalSearch(String type, String keyword);
}
