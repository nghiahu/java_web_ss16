package com.example.session16.service.busTrip;

import com.example.session16.model.BusTrip;
import com.example.session16.repository.busTrip.BusTripRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusTripServiceImp implements BusTripService {

    @Autowired
    private BusTripRepositoryImp busTripRepositoryImp;

    @Override
    public List<BusTrip> getAllBusTrips(int page_size, int current_page) {
        return busTripRepositoryImp.getAllBusTrips(page_size, current_page);
    }

    @Override
    public int totalBusTrips() {
        return busTripRepositoryImp.totalBusTrips();
    }

    @Override
    public List<BusTrip> searchBusTrips(String type, String keyword, int page_size, int current_page) {
        return busTripRepositoryImp.searchBusTrips(type, keyword, page_size, current_page);
    }

    @Override
    public int totalSearch(String type, String keyword) {
        return busTripRepositoryImp.totalSearch(type, keyword);
    }
}
