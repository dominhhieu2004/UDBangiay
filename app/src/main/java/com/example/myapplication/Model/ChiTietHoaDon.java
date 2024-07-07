package com.example.myapplication.Model;

public class ChiTietHoaDon {
    private int maCTHD;

    private int maHoaDon;
    private String imgaesCTHD;

    private int maSPCTHD,soLuong;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int maCTHD, int maHoaDon, String imgaesCTHD, int maSPCTHD, int soLuong) {
        this.maCTHD = maCTHD;
        this.maHoaDon = maHoaDon;
        this.imgaesCTHD = imgaesCTHD;
        this.maSPCTHD = maSPCTHD;
        this.soLuong = soLuong;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public int getMaCTHD() {
        return maCTHD;
    }

    public void setMaCTHD(int maCTHD) {
        this.maCTHD = maCTHD;
    }

    public String getImgaesCTHD() {
        return imgaesCTHD;
    }

    public void setImgaesCTHD(String imgaesCTHD) {
        this.imgaesCTHD = imgaesCTHD;
    }

    public int getMaSPCTHD() {
        return maSPCTHD;
    }

    public void setMaSPCTHD(int maSPCTHD) {
        this.maSPCTHD = maSPCTHD;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
