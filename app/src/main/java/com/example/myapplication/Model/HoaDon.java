package com.example.myapplication.Model;

public class HoaDon {
    private int maHoaDon;

    private String maKH;
    private int maSP, tongTien;

    private int soLuong;
    private String imgaesHD, ngayDat;

    public HoaDon() {
    }

    public HoaDon(int maHoaDon, String maKH, int maSP, int tongTien, int soLuong, String imgaesHD, String ngayDat) {
        this.maHoaDon = maHoaDon;
        this.maKH = maKH;
        this.maSP = maSP;
        this.tongTien = tongTien;
        this.soLuong = soLuong;
        this.imgaesHD = imgaesHD;
        this.ngayDat = ngayDat;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getImgaesHD() {
        return imgaesHD;
    }

    public void setImgaesHD(String imgaesHD) {
        this.imgaesHD = imgaesHD;
    }

    public String getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(String ngayDat) {
        this.ngayDat = ngayDat;
    }
}
