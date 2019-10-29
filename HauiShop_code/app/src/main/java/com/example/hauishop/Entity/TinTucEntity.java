package com.example.hauishop.Entity;

import java.io.Serializable;

public class TinTucEntity implements Serializable {
    private int maTT;
    private String tenTT;
    private String ngayDang;
    private String noiDung;
    private String image;

    public TinTucEntity() {
    }

    public TinTucEntity(int maTT, String tenTT, String ngayDang, String noiDung, String image) {
        this.maTT = maTT;
        this.tenTT = tenTT;
        this.ngayDang = ngayDang;
        this.noiDung = noiDung;
        this.image = image;
    }

    public int getMaTT() {
        return maTT;
    }

    public void setMaTT(int maTT) {
        this.maTT = maTT;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
    }

    public String getNgayDang() {
        return ngayDang;
    }

    public void setNgayDang(String ngayDang) {
        this.ngayDang = ngayDang;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
