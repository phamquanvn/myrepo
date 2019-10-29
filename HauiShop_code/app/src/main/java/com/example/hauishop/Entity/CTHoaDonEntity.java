package com.example.hauishop.Entity;

public class CTHoaDonEntity {
    private int maHD;
    private int maSP;
    private String image;
    private String tenSP;
    private int soLuong;
    private int giaBan;
    private String trangThai;

    public CTHoaDonEntity() {
    }

    public CTHoaDonEntity(int maHD, int maSP, String image, String tenSP, int soLuong, int giaBan, String trangThai) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.image = image;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.trangThai = trangThai;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }
}
