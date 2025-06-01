package com.example.session16.service.bus;

import com.example.session16.model.Bus;
import com.example.session16.repository.bus.BusRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImp implements BusService {
    @Autowired
    private BusRepositoryImp busRepositoryImp;

    @Override
    public List<Bus> getAllBuses() {
        return busRepositoryImp.getAllBuses();
    }

    @Override
    public boolean addBus(Bus bus) {
        return busRepositoryImp.addBus(bus);
    }

    @Override
    public boolean updateBus(Bus bus) {
        return busRepositoryImp.updateBus(bus);
    }

    @Override
    public boolean deleteBus(int bus_id) {
        return busRepositoryImp.deleteBus(bus_id);
    }

    @Override
    public Bus getBusById(int bus_id) {
        return busRepositoryImp.getBusById(bus_id);
    }
}
