package com.example.session16.repository.bus;

import com.example.session16.model.Bus;

import java.util.List;

public interface BusRepository {
    List<Bus> getAllBuses();
    boolean addBus(Bus bus);
    boolean updateBus(Bus bus);
    boolean deleteBus(int bus_id);
    Bus getBusById(int bus_id);
}
