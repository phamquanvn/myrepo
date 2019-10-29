package com.example.hauishop.ImageLink;

import com.example.hauishop.R;

import java.util.HashMap;

public class ImageLink {
    public static int getResourceImage(int id) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(11, R.drawable.ic_female);
        map.put(12, R.drawable.ic_male);
        map.put(1, R.drawable.ic_rau_an_la);
        map.put(2, R.drawable.ic_cu);
        map.put(3, R.drawable.ic_egg);
        map.put(4, R.drawable.ic_ga);
        map.put(5, R.drawable.ic_gia_vi_nuoc);
        map.put(6, R.drawable.ic_rau_gia_vi);
        map.put(7, R.drawable.ic_sausage);
        map.put(8, R.drawable.ic_meat);
        map.put(9, R.drawable.ic_qua);
        map.put(10, R.drawable.ic_qua_nhap_khau);

        return map.get(id);
    }
}
