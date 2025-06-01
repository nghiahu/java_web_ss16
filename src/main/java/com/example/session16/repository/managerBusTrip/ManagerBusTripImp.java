package com.example.session16.repository.managerBusTrip;

import com.example.session16.config.ConnectionDB;
import com.example.session16.model.BusTrip;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ManagerBusTripImp implements ManagerBusTrip {
    @Override
    public List<BusTrip> getAllBusTrips() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<BusTrip> busTrips = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_bustrips()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                BusTrip bt = new BusTrip();
                bt.setId(rs.getInt("id"));
                bt.setDeparturePoint(rs.getString("departurePoint"));
                bt.setDestination(rs.getString("destination"));
                bt.setDepartureTime(rs.getTimestamp("departureTime").toLocalDateTime());
                bt.setArrivalTime(rs.getTimestamp("arrivalTime").toLocalDateTime());
                bt.setBusId(rs.getInt("busId"));
                bt.setSeatsAvailable(rs.getInt("seatsAvailable"));
                bt.setImage(rs.getString("image"));
                busTrips.add(bt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return busTrips;
    }

    @Override
    public BusTrip getBusTrip(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        BusTrip bt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_bustrip_by_id(?)}");
            callSt.setInt(1, id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                bt = new BusTrip();
                bt.setId(rs.getInt("id"));
                bt.setDeparturePoint(rs.getString("departurePoint"));
                bt.setDestination(rs.getString("destination"));
                bt.setDepartureTime(rs.getTimestamp("departureTime").toLocalDateTime());
                bt.setArrivalTime(rs.getTimestamp("arrivalTime").toLocalDateTime());
                bt.setBusId(rs.getInt("busId"));
                bt.setSeatsAvailable(rs.getInt("seatsAvailable"));
                bt.setImage(rs.getString("image"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return bt;
    }

    @Override
    public boolean addBusTrip(BusTrip busTrip) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call insert_bustrip(?,?,?,?,?,?,?)}");
            callSt.setString(1, busTrip.getDeparturePoint());
            callSt.setString(2, busTrip.getDestination());
            callSt.setTimestamp(3, Timestamp.valueOf(busTrip.getDepartureTime()));
            callSt.setTimestamp(4, Timestamp.valueOf(busTrip.getArrivalTime()));
            callSt.setInt(5, busTrip.getBusId());
            callSt.setInt(6, busTrip.getSeatsAvailable());
            callSt.setString(7, busTrip.getImage());
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean updateBusTrip(BusTrip busTrip) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_bustrip(?,?,?,?,?,?,?,?)}");
            callSt.setInt(1, busTrip.getId());
            callSt.setString(2, busTrip.getDeparturePoint());
            callSt.setString(3, busTrip.getDestination());
            callSt.setTimestamp(4, Timestamp.valueOf(busTrip.getDepartureTime()));
            callSt.setTimestamp(5, Timestamp.valueOf(busTrip.getArrivalTime()));
            callSt.setInt(6, busTrip.getBusId());
            callSt.setInt(7, busTrip.getSeatsAvailable());
            callSt.setString(8, busTrip.getImage());
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean deleteBusTrip(int id) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call delete_bustrip(?)}");
            callSt.setInt(1, id);
            callSt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }
}
