package com.example.hauishop;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hauishop.API.APIServer;
import com.example.hauishop.Adapter.CT_HoaDon_Adapter;
import com.example.hauishop.Adapter.HoaDonAdapter;
import com.example.hauishop.Entity.CTHoaDonEntity;
import com.example.hauishop.Entity.HoaDonEntity;
import com.example.hauishop.Entity.KhachHangEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HoaDonActivity extends AppCompatActivity {

    ListView lvHD;
    ArrayList<HoaDonEntity> arrHD;
    ArrayList<CTHoaDonEntity> arrCT;
    HoaDonAdapter hoaDonAdapter;
    CT_HoaDon_Adapter ct_hoaDon_adapter;
    UserLocal userLocal;
    APIServer api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        addControls();
        addEvents();
    }

    private void addEvents() {
        lvHD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialog dialog = new Dialog(HoaDonActivity.this);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialoghoadon);

                ListView lvHD = dialog.findViewById(R.id.lvHD);
                arrCT = new ArrayList<>();
                ct_hoaDon_adapter = new CT_HoaDon_Adapter(HoaDonActivity.this, R.layout.item_dialog, arrCT);
                lvHD.setAdapter(ct_hoaDon_adapter);
                GetCTHD(api.HostingAPI + api.get_CTHD, arrHD.get(position).getMaHD());
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            }
        });
    }

    private void addControls() {
        lvHD = findViewById(R.id.lvHD);
        arrHD = new ArrayList<>();
        hoaDonAdapter = new HoaDonAdapter(HoaDonActivity.this, R.layout.item_hoa_don, arrHD);
        lvHD.setAdapter(hoaDonAdapter);
        if (userLocal.kh == null) {
            Toast.makeText(this, "Hãy đăng nhập để tiếp tục", Toast.LENGTH_SHORT).show();
        } else {
            GetHoaDon(api.HostingAPI + api.get_HoaDon, userLocal.kh.getMaKH());
        }

    }

    private void GetHoaDon(String url, final int maKH) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        arrHD.add(new HoaDonEntity(object.getInt("Ma_hoa_don"), object.getString("Hoten_nguoinhan"), object.getString("Diachi_nguoinhan"), object.getString("Dienthoai_nguoinhan"), object.getString("Email_nguoinhan"), object.getString("Ngay_lap_hd"), object.getInt("Tong_tien"), object.getInt("Ma_khach_hang"), object.getString("Trang_thai")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hoaDonAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maKH", maKH + "");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetCTHD(String url, final int maHD) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        arrCT.add(new CTHoaDonEntity(object.getInt("Ma_hoa_don"), object.getInt("Ma_san_pham"), object.getString("Image"), object.getString("Ten_sp"), object.getInt("Gia_ban"), object.getInt("So_luong"), object.getString("Trang_thai")));
                        Log.e("stt", "STT :" + (i + 1) + " : " + arrCT.get(i).getTrangThai());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ct_hoaDon_adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maHD", maHD + "");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HoaDonActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}
