package com.example.session16.service.managerBustrip;

import com.example.session16.model.BusTrip;

import java.util.List;

public interface ManagerBusTripService {
    List<BusTrip> getAllBusTrips();
    BusTrip getBusTrip(int id);
    boolean addBusTrip(BusTrip busTrip);
    boolean updateBusTrip(BusTrip busTrip);
    boolean deleteBusTrip(int id);
}
