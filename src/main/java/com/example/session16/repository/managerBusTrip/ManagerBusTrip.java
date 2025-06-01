package com.example.session16.repository.managerBusTrip;

import com.example.session16.model.BusTrip;

import java.util.List;

public interface ManagerBusTrip {
    List<BusTrip> getAllBusTrips();
    BusTrip getBusTrip(int id);
    boolean addBusTrip(BusTrip busTrip);
    boolean updateBusTrip(BusTrip busTrip);
    boolean deleteBusTrip(int id);
}
