package com.example.session16.service.managerBustrip;

import com.example.session16.model.BusTrip;
import com.example.session16.repository.managerBusTrip.ManagerBusTripImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerBusTripServiceImp implements ManagerBusTripService {
    @Autowired
    private ManagerBusTripImp managerBusTripImp;

    @Override
    public List<BusTrip> getAllBusTrips() {
        return managerBusTripImp.getAllBusTrips();
    }

    @Override
    public BusTrip getBusTrip(int id) {
        return managerBusTripImp.getBusTrip(id);
    }

    @Override
    public boolean addBusTrip(BusTrip busTrip) {
        return managerBusTripImp.addBusTrip(busTrip);
    }

    @Override
    public boolean updateBusTrip(BusTrip busTrip) {
        return managerBusTripImp.updateBusTrip(busTrip);
    }

    @Override
    public boolean deleteBusTrip(int id) {
        return managerBusTripImp.deleteBusTrip(id);
    }
}
