package com.example.hauishop;

import com.example.hauishop.Entity.KhachHangEntity;

public class UserLocal {
    static KhachHangEntity kh = null;

    public static KhachHangEntity getKh() {
        return kh;
    }

    public static void setKh(KhachHangEntity kh) {
        UserLocal.kh = kh;
    }
}
