package com.example.session16.repository.busTrip;

import com.example.session16.config.ConnectionDB;
import com.example.session16.model.BusTrip;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BusTripRepositoryImp implements BusTripRepository {

    @Override
    public List<BusTrip> getAllBusTrips(int page_size, int current_page) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<BusTrip> busTrips = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call getAllProcedurePagination(?,?)}");
            callSt.setInt(1, page_size);
            callSt.setInt(2, current_page);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                BusTrip busTrip = new BusTrip();
                busTrip.setId(rs.getInt("id"));
                busTrip.setDeparturePoint(rs.getString("departurePoint"));
                busTrip.setDestination(rs.getString("destination"));
                busTrip.setDepartureTime(rs.getTimestamp("departureTime").toLocalDateTime());
                busTrip.setArrivalTime(rs.getTimestamp("arrivalTime").toLocalDateTime());
                busTrip.setBusId(rs.getInt("busId"));
                busTrip.setSeatsAvailable(rs.getInt("seatsAvailable"));
                busTrip.setImage(rs.getString("image"));
                busTrips.add(busTrip);
            }
            return busTrips;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return busTrips;
    }

    @Override
    public int totalBusTrips() {
        Connection conn = null;
        CallableStatement callSt = null;
        int total = 0;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call totalBusTrip()}");
            callSt.execute();
            ResultSet rs = callSt.getResultSet();
            if (rs.next()) {
                total = rs.getInt(1);
                return total;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return total;
    }

    @Override
    public List<BusTrip> searchBusTrips(String type, String keyword, int page_size, int current_page) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<BusTrip> busTrips = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call searchBusTrips(?,?,?,?)}");
            callSt.setString(1, type);
            callSt.setString(2, keyword);
            callSt.setInt(3, page_size);
            callSt.setInt(4, current_page);
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                BusTrip busTrip = new BusTrip();
                busTrip.setId(rs.getInt("id"));
                busTrip.setDeparturePoint(rs.getString("departurePoint"));
                busTrip.setDestination(rs.getString("destination"));
                busTrip.setDepartureTime(rs.getTimestamp("departureTime").toLocalDateTime());
                busTrip.setArrivalTime(rs.getTimestamp("arrivalTime").toLocalDateTime());
                busTrip.setBusId(rs.getInt("busId"));
                busTrip.setSeatsAvailable(rs.getInt("seatsAvailable"));
                busTrip.setImage(rs.getString("image"));
                busTrips.add(busTrip);
            }
            return busTrips;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return busTrips;
    }

    @Override
    public int totalSearch(String type, String keyword) {
        Connection conn = null;
        CallableStatement callSt = null;
        int total = 0;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call totalSearch(?,?)}");
            callSt.setString(1, type);
            callSt.setString(2, keyword);
            callSt.execute();
            ResultSet rs = callSt.getResultSet();
            if (rs.next()) {
                total = rs.getInt(1);
                return total;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return total;
    }
}

