package com.example.hauishop.Entity;

import java.io.Serializable;
import java.util.Objects;

public class GioHangEntity implements Serializable {
    private int maKH;
    private int maSP;
    private String tenSP;
    private int soLuong;
    private int giaBan;
    private String image;

    public GioHangEntity() {
    }

    public GioHangEntity(int maKH, int maSP, String tenSP, int soLuong, int giaBan, String image) {
        this.maKH = maKH;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.image = image;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GioHangEntity that = (GioHangEntity) o;
        return maSP == that.maSP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSP);
    }
}
