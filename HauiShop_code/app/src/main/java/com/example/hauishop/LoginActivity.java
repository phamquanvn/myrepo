package com.example.hauishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hauishop.API.APIServer;
import com.example.hauishop.Entity.KhachHangEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau;
    UserLocal userLocal;
    APIServer api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControl();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControl() {
        edtTaiKhoan = findViewById(R.id.txtLoginTenDN);
        edtMatKhau = findViewById(R.id.txtLoginMatKhau);
    }

    public void XuLyDangNhap(View view) {
        Login(api.HostingAPI + api.login);
    }

    public void XuLyDangKy(View view) {
        startActivity(new Intent(LoginActivity.this, DangKyActivity.class));
    }

    private void Login(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().compareTo("1") == 0) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    getKhachHangLogin(api.HostingAPI + api.get_KhachHang_Login);
                } else {
                    Toast.makeText(LoginActivity.this, "Tài khoản , mật khẩu không chính xác hoặc tài khoản của bạn đã bị khóa", Toast.LENGTH_SHORT).show();
                    edtTaiKhoan.requestFocus();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
                Log.e("er", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("taiKhoan", edtTaiKhoan.getText().toString().trim());
                map.put("matKhau", edtMatKhau.getText().toString().trim());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getKhachHangLogin(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        KhachHangEntity kh = new KhachHangEntity(object.getInt("Ma_khach_hang"), object.getString("Ho_ten_kh"), object.getString("Dia_chi"), object.getString("Dien_thoai"),object.getString("Email"),object.getString("Ngay_sinh"),object.getString("Gioi_tinh"),object.getString("Tai_khoan"),object.getString("Mat_khau"),object.getString("Trang_thai"));
                        userLocal.setKh(kh);
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("taiKhoan", edtTaiKhoan.getText().toString().trim());
                map.put("matKhau", edtMatKhau.getText().toString().trim());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}
