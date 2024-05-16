package com.example.taxi;

import java.io.Serializable;

public class Taxi implements Serializable {
    int Id;
    String SoXe;
    Double QuangDuong;
    Double DonGia;
    int PhanTram;

    public Taxi(int id, String soXe, Double quangDuong, Double donGia, int phanTram) {
        Id = id;
        SoXe = soXe;
        QuangDuong = quangDuong;
        DonGia = donGia;
        PhanTram = phanTram;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setSoXe(String soXe) {
        SoXe = soXe;
    }

    public void setQuangDuong(Double quangDuong) {
        QuangDuong = quangDuong;
    }

    public void setDonGia(Double donGia) {
        DonGia = donGia;
    }

    public void setPhanTram(int phanTram) {
        PhanTram = phanTram;
    }

    public int getId() {
        return Id;
    }

    public String getSoXe() {
        return SoXe;
    }

    public Double getQuangDuong() {
        return QuangDuong;
    }

    public Double getDonGia() {
        return DonGia;
    }

    public int getPhanTram() {
        return PhanTram;
    }
}
