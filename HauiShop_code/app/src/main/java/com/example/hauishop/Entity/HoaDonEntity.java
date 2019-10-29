package com.example.hauishop.Entity;

public class HoaDonEntity {
    private int maHD;
    private String hoTen;
    private String diaChi;
    private String dienThoai;
    private String email;
    private String ngayLap;
    private int tongTien;
    private int maKH;
    private String trangThai;

    public HoaDonEntity() {
    }

    public HoaDonEntity(int maHD, String hoTen, String diaChi, String dienThoai, String email, String ngayLap, int tongTien, int maKH, String trangThai) {
        this.maHD = maHD;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
        this.email = email;
        this.ngayLap = ngayLap;
        this.tongTien = tongTien;
        this.maKH = maKH;
        this.trangThai = trangThai;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }
}
