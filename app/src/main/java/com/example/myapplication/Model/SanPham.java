package com.example.myapplication.Model;

public class SanPham {
    private int maSP;
    private String tenSP;
    private int maHang;

    private String moTa;

    private int giaTien;

    private int soLuong;

    private String images;

    public SanPham() {
    }

    public SanPham(int maSP, String tenSP, int maHang, String moTa, int giaTien, int soLuong, String images) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.maHang = maHang;
        this.moTa = moTa;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
        this.images = images;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
