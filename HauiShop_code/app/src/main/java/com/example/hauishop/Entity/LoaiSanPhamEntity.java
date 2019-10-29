package com.example.hauishop.Entity;

public class LoaiSanPhamEntity {

    private int maLoaiSP;
    private String tenLoaiSP;
    private String imgAnh;

    public LoaiSanPhamEntity() {
    }

    public LoaiSanPhamEntity(int maLoaiSP, String tenLoaiSP, String imgAnh) {
        this.maLoaiSP = maLoaiSP;
        this.tenLoaiSP = tenLoaiSP;
        this.imgAnh = imgAnh;
    }

    public String getImgAnh() {
        return imgAnh;
    }

    public void setImgAnh(String imgAnh) {
        this.imgAnh = imgAnh;
    }

    public int getMaLoaiSP() {
        return maLoaiSP;
    }

    public void setMaLoaiSP(int maLoaiSP) {
        this.maLoaiSP = maLoaiSP;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }
}
