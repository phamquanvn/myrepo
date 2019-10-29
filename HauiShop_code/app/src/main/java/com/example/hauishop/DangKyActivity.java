package com.example.hauishop;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hauishop.API.APIServer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DangKyActivity extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau, edtHoTen, edtDiaChi, edtDienThoai, edtEmail, edtNgaySinh, edtReMatKhau;
    RadioGroup rdoGioiTinh;
    Button btnDangKi, btnHuy;
    APIServer api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        addControls();
        addEvents();
    }

    private void addEvents() {
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtNgaySinh.setText(format(year, month, dayOfMonth));
                    }
                };
                Calendar calendar = Calendar.getInstance();
                int y = calendar.get(Calendar.YEAR);
                int m = calendar.get(Calendar.MONTH);
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(DangKyActivity.this, odsl, y, m, d);
                dialog.show();
            }
        });
    }

    private String format(int year, int month, int dayOfMonth) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        return format.format(c.getTime());
    }

    private void addControls() {

        edtTaiKhoan = findViewById(R.id.edtDKTaiKhoan);
        edtMatKhau = findViewById(R.id.edtDKMatKhau);
        edtHoTen = findViewById(R.id.edtDKHoTen);
        edtDiaChi = findViewById(R.id.edtDKDiaChi);
        edtDienThoai = findViewById(R.id.edtDKDienThoai);
        edtEmail = findViewById(R.id.edtDKEmail);
        edtNgaySinh = findViewById(R.id.edtDKNgaySinh);
        edtReMatKhau = findViewById(R.id.edtDKReMatKhau);

        rdoGioiTinh = findViewById(R.id.radGioiTinh);

        btnDangKi = findViewById(R.id.btnDKDangKi);
        btnHuy = findViewById(R.id.btnHuy);
    }

    public void xuLyDangKy(View view) {
        KiemTraTaiKhoan(api.HostingAPI + api.kiemtra_TaiKhoan_KhachHang);
    }

    public void xuLyHuy(View view) {
        startActivity(new Intent(DangKyActivity.this, LoginActivity.class));
    }

    private void KiemTraTaiKhoan(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().compareTo("1") == 0) {
                    Toast.makeText(DangKyActivity.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    edtTaiKhoan.requestFocus();
                } else {
                    if (edtHoTen.getText().toString().compareTo("") == 0) {
                        Toast.makeText(DangKyActivity.this, "Họ tên không được để trống", Toast.LENGTH_SHORT).show();
                        edtHoTen.requestFocus();
                        return;
                    }
                    if (edtDiaChi.getText().toString().compareTo("") == 0) {
                        Toast.makeText(DangKyActivity.this, "Địa chỉ không được để trống", Toast.LENGTH_SHORT).show();
                        edtDiaChi.requestFocus();
                        return;
                    }
                    if (edtDienThoai.getText().toString().compareTo("") == 0) {
                        Toast.makeText(DangKyActivity.this, "Số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
                        edtDienThoai.requestFocus();
                        return;
                    }
                    if (edtEmail.getText().toString().compareTo("") == 0) {
                        Toast.makeText(DangKyActivity.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
                        edtEmail.requestFocus();
                        return;
                    }
                    if (edtNgaySinh.getText().toString().compareTo("") == 0) {
                        Toast.makeText(DangKyActivity.this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
                        edtNgaySinh.requestFocus();
                        return;
                    }
                    if (edtTaiKhoan.getText().toString().compareTo("") == 0) {
                        Toast.makeText(DangKyActivity.this, "Tài khoản không được để trống", Toast.LENGTH_SHORT).show();
                        edtTaiKhoan.requestFocus();
                        return;
                    }
                    if (edtMatKhau.getText().toString().compareTo("") == 0) {
                        Toast.makeText(DangKyActivity.this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                        edtMatKhau.requestFocus();
                        return;
                    }
                    if (edtReMatKhau.getText().toString().compareTo(edtMatKhau.getText().toString()) != 0) {
                        Toast.makeText(DangKyActivity.this, "Xác nhận mật khẩu chưa trùng khớp", Toast.LENGTH_SHORT).show();
                        edtMatKhau.requestFocus();
                        return;
                    }
                    InsertKhachHang(api.HostingAPI + api.insert_KhachHang);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangKyActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
                Log.e("er", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("taiKhoan", edtTaiKhoan.getText().toString().trim());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void InsertKhachHang(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().compareTo("1") == 0) {
                    Toast.makeText(DangKyActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DangKyActivity.this, LoginActivity.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangKyActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
                Log.e("er", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("taiKhoan", edtTaiKhoan.getText().toString().trim());
                map.put("matKhau", edtMatKhau.getText().toString().trim());
                map.put("hoTen", edtHoTen.getText().toString().trim());
                map.put("diaChi", edtDiaChi.getText().toString().trim());
                map.put("soDienThoai", edtDienThoai.getText().toString().trim());
                map.put("email", edtEmail.getText().toString().trim());
                map.put("ngaySinh", edtNgaySinh.getText().toString().trim());
                int id = rdoGioiTinh.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rdoNam: {
                        map.put("gioiTinh", "Nam");
                        break;
                    }
                    case R.id.rdoNu: {
                        map.put("gioiTinh", "Nữ");
                        break;
                    }
                }
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
