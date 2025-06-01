package com.example.session16.model;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Bus {
    private int id;
    @NotBlank(message = "Biển số xe không được để trống")
    @Pattern(regexp = "^\\d{2}B-\\d{4,5}$",message = "Biển số xe không đúng định dạng")
    private String licensePlate;
    @NotNull(message = "Loại xe không được để trống")
    private BusType busType;
    @Min(value = 1, message = "Số hàng ghế không hợp lệ")
    private int rowSeat;
    @Min(value = 1, message = "Số cột ghế không hợp lệ")
    private int colSeat;
    private int totalSeat;
    private String image;

    private transient MultipartFile file;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public BusType getBusType() {
        return busType;
    }

    public void setBusType(BusType busType) {
        this.busType = busType;
    }

    public int getRowSeat() {
        return rowSeat;
    }

    public void setRowSeat(int rowSeat) {
        this.rowSeat = rowSeat;
    }

    public int getColSeat() {
        return colSeat;
    }

    public void setColSeat(int colSeat) {
        this.colSeat = colSeat;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public void setTotalSeat(int totalSeat) {
        this.totalSeat = totalSeat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
