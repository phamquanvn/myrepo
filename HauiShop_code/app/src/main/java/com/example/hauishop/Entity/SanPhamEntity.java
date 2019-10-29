package com.example.hauishop.Entity;

import java.io.Serializable;

public class SanPhamEntity implements Serializable {

    private int maSP;
    private String tenSP;
    private String thongTinSP;
    private int giaNhap;
    private int giaCu;
    private int giaMoi;
    private String ngayMoBan;
    private int soLuong;
    private String image;
    private int maNCC;
    private int maLoaiSP;
    private String trangThai;

    public SanPhamEntity(int maSP, String tenSP, int giaCu, int giaMoi, String image) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaCu = giaCu;
        this.giaMoi = giaMoi;
        this.image = image;
    }

    public SanPhamEntity() {
    }

    public String getNgayMoBan() {
        return ngayMoBan;
    }

    public void setNgayMoBan(String ngayMoBan) {
        this.ngayMoBan = ngayMoBan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public SanPhamEntity(int maSP, String tenSP, String thongTinSP, int giaNhap, int giaCu, int giaMoi, String ngayMoBan, int soLuong, String image, int maNCC, int maLoaiSP, String trangThai) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.thongTinSP = thongTinSP;
        this.giaNhap = giaNhap;
        this.giaCu = giaCu;
        this.giaMoi = giaMoi;
        this.ngayMoBan = ngayMoBan;
        this.soLuong = soLuong;
        this.image = image;
        this.maNCC = maNCC;
        this.maLoaiSP = maLoaiSP;
        this.trangThai = trangThai;
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

    public String getThongTinSP() {
        return thongTinSP;
    }

    public void setThongTinSP(String thongTinSP) {
        this.thongTinSP = thongTinSP;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getGiaCu() {
        return giaCu;
    }

    public void setGiaCu(int giaCu) {
        this.giaCu = giaCu;
    }

    public int getGiaMoi() {
        return giaMoi;
    }

    public void setGiaMoi(int giaMoi) {
        this.giaMoi = giaMoi;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaNCC() {
        return maNCC;
    }

    public void setMaNCC(int maNCC) {
        this.maNCC = maNCC;
    }

    public int getMaLoaiSP() {
        return maLoaiSP;
    }

    public void setMaLoaiSP(int maLoaiSP) {
        this.maLoaiSP = maLoaiSP;
    }

    public String isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SanPhamEntity that = (SanPhamEntity) o;
        return maSP == that.maSP;
    }

}
