package com.example.hauishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hauishop.API.APIServer;
import com.example.hauishop.Entity.GioHangEntity;
import com.example.hauishop.Entity.KhachHangEntity;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {

    EditText edtHoTen, edtDiaChi, edtDienThoai, edtEmail, edtNgayLap;
    TextView txtTong;
    Button btnThanhToan;
    ArrayList<GioHangEntity> arrGio;
    UserLocal userLocal;
    APIServer api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        arrGio = new ArrayList<>();
        edtHoTen = findViewById(R.id.edtHDHoTen);
        edtDiaChi = findViewById(R.id.edtHDDiaChi);
        edtDienThoai = findViewById(R.id.edtHDDienThoai);
        edtEmail = findViewById(R.id.edtHDEmail);
        edtNgayLap = findViewById(R.id.edtHDNgayLap);
        txtTong = findViewById(R.id.txtHDTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToanFinal);
        edtHoTen.setText(userLocal.kh.getTenKH());
        edtDiaChi.setText(userLocal.kh.getDiaChi());
        edtDienThoai.setText(userLocal.kh.getDienThoai());
        edtEmail.setText(userLocal.kh.getEmail());

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        arrGio = (ArrayList<GioHangEntity>) bundle.getSerializable("data1");
        int tong = 0;
        for (int i = 0; i < arrGio.size(); i++) {
            tong += arrGio.get(i).getSoLuong() * arrGio.get(i).getGiaBan();
        }
        txtTong.setText(tong + "");
        Date date = new Date();
        edtNgayLap.setText(format(date));
    }

    private String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return format.format(c.getTime());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ThanhToanActivity.this, GioHangActivity.class));
        super.onBackPressed();
    }

    private void DeleteGioHang(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThanhToanActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maKH", userLocal.getKh().getMaKH() + "");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void InsertHD(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < arrGio.size(); i++) {
                        try {
                            InsertCTHD(api.HostingAPI + api.insert_ChiTiet_HD, array.getJSONObject(0).getInt("Ma_hoa_don"), arrGio.get(i).getMaSP(), arrGio.get(i).getSoLuong(), arrGio.get(i).getGiaBan());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThanhToanActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("hoTen", edtHoTen.getText().toString().trim());
                map.put("diaChi", edtDiaChi.getText().toString().trim());
                map.put("dienThoai", edtDienThoai.getText().toString().trim());
                map.put("email", edtEmail.getText().toString().trim());
                map.put("ngayLap", edtNgayLap.getText().toString().trim());
                map.put("tongTien", txtTong.getText().toString().trim());
                map.put("maKH", userLocal.getKh().getMaKH() + "");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void xuLyDangKyFinal(View view) {
        if (edtHoTen.getText().toString().compareTo("") == 0) {
            Toast.makeText(ThanhToanActivity.this, "Họ tên người nhận không được để trống", Toast.LENGTH_SHORT).show();
            edtHoTen.requestFocus();
            return;
        }
        if (edtDiaChi.getText().toString().compareTo("") == 0) {
            Toast.makeText(ThanhToanActivity.this, "Địa chỉ người nhận không được để trống", Toast.LENGTH_SHORT).show();
            edtDiaChi.requestFocus();
            return;
        }
        if (edtDienThoai.getText().toString().compareTo("") == 0) {
            Toast.makeText(ThanhToanActivity.this, "Điện thoại người nhận không được để trống", Toast.LENGTH_SHORT).show();
            edtDienThoai.requestFocus();
            return;
        }
        if (edtEmail.getText().toString().compareTo("") == 0) {
            Toast.makeText(ThanhToanActivity.this, "Email người nhận không được để trống", Toast.LENGTH_SHORT).show();
            edtEmail.requestFocus();
            return;
        }
        InsertHD(api.HostingAPI + api.insert_HoaDon);
        DeleteGioHang(api.HostingAPI + api.delete_All_GioHang);
        Toast.makeText(ThanhToanActivity.this, "Lập hóa đơn thành công", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ThanhToanActivity.this, HoaDonActivity.class));
    }

    private void InsertCTHD(String url, final int maHD, final int maSP, final int soLuong, final double giaBan) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThanhToanActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maHD", maHD + "");
                map.put("maSP", maSP + "");
                map.put("soLuong", soLuong + "");
                map.put("giaBan", giaBan + "");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
