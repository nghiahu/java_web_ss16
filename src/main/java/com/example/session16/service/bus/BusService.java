package com.example.session16.service.bus;

import com.example.session16.model.Bus;

import java.util.List;

public interface BusService {
    List<Bus> getAllBuses();
    boolean addBus(Bus bus);
    boolean updateBus(Bus bus);
    boolean deleteBus(int bus_id);
    Bus getBusById(int bus_id);
}
