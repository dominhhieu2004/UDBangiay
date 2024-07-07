package com.example.myapplication.Model;

public class QTV {
    private String maQTV;
    private String matKhau;

    public QTV() {
    }

    public QTV(String maQTV, String matKhau) {
        this.maQTV = maQTV;
        this.matKhau = matKhau;
    }

    public String getMaQTV() {
        return maQTV;
    }

    public void setMaQTV(String maQTV) {
        this.maQTV = maQTV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
