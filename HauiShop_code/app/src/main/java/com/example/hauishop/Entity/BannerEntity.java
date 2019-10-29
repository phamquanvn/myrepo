package com.example.hauishop.Entity;

public class BannerEntity {
    private int maHA;
    private String tenAnh;
    private String image;

    public BannerEntity() {
    }

    public BannerEntity(int maHA, String tenAnh, String image) {
        this.maHA = maHA;
        this.tenAnh = tenAnh;
        this.image = image;
    }

    public int getMaHA() {
        return maHA;
    }

    public void setMaHA(int maHA) {
        this.maHA = maHA;
    }

    public String getTenAnh() {
        return tenAnh;
    }

    public void setTenAnh(String tenAnh) {
        this.tenAnh = tenAnh;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
