package com.example.session16.repository.bus;

import com.example.session16.config.ConnectionDB;
import com.example.session16.model.Bus;
import com.example.session16.model.BusType;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BusRepositoryImp implements BusRepository {
    @Override
    public List<Bus> getAllBuses() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Bus> buses = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call getAllBus()}");
            ResultSet rs = callSt.executeQuery();
            while (rs.next()) {
                Bus bus = new Bus();
                bus.setId(rs.getInt("id"));
                bus.setLicensePlate(rs.getString("licensePlate"));
                bus.setBusType(BusType.valueOf(rs.getString("busType")));
                bus.setRowSeat(rs.getInt("rowSeat"));
                bus.setColSeat(rs.getInt("colSeat"));
                bus.setTotalSeat(rs.getInt("totalSeat"));
                bus.setImage(rs.getString("image"));
                buses.add(bus);
            }
            return buses;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return buses;
    }

    @Override
    public boolean addBus(Bus bus) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call createBus(?,?,?,?,?)}");
            callSt.setString(1, bus.getLicensePlate());
            callSt.setString(2,bus.getBusType().toString());
            callSt.setInt(3,bus.getRowSeat());
            callSt.setInt(4,bus.getColSeat());
            callSt.setString(5,bus.getImage());
            callSt.execute();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean updateBus(Bus bus) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call updateBus(?,?,?,?)}");
            callSt.setInt(1,bus.getId());
            callSt.setString(2,bus.getLicensePlate());
            callSt.setString(3,bus.getBusType().toString());
            callSt.setString(4,bus.getImage());
            callSt.execute();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean deleteBus(int bus_id) {
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call deleteBus(?)");
            callSt.setInt(1,bus_id);
            callSt.execute();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public Bus getBusById(int bus_id) {
        Connection conn = null;
        CallableStatement callSt = null;
        Bus bus = null;
        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call getBusById(?)}");
            callSt.setInt(1,bus_id);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                bus = new Bus();
                bus.setId(rs.getInt("id"));
                bus.setLicensePlate(rs.getString("licensePlate"));
                bus.setBusType(BusType.valueOf(rs.getString("busType")));
                bus.setRowSeat(rs.getInt("rowSeat"));
                bus.setColSeat(rs.getInt("colSeat"));
                bus.setTotalSeat(rs.getInt("totalSeat"));
                bus.setImage(rs.getString("image"));
                return bus;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return bus;
    }
}
