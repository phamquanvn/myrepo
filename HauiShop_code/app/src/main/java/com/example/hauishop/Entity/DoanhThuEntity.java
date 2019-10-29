package com.example.hauishop.Entity;

import java.util.Objects;

public class DoanhThuEntity {
    public int maHD;
    public int tongSoLuong;
    public int tongTien;
    public String ngayLap;

    public DoanhThuEntity(int maHD, int tongSoLuong, int tongTien, String ngayLap) {
        this.maHD = maHD;
        this.tongSoLuong = tongSoLuong;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
    }

    public DoanhThuEntity() {

    }

    @Override
    public boolean equals(Object o) {
        DoanhThuEntity that = (DoanhThuEntity) o;
        String date=that.ngayLap;
        String month= date.substring(date.indexOf("-")+1,date.lastIndexOf("-"));
        String monthOri= ngayLap.substring(ngayLap.indexOf("-")+1,ngayLap.lastIndexOf("-"));
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        return Objects.equals(monthOri, month);
    }

    @Override
    public String toString() {
        return
                "  " + maHD +
                "  " + tongSoLuong +
                "  "  + tongTien +
                "  " + ngayLap ;
    }
}
