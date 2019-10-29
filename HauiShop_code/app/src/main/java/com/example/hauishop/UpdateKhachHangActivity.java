package com.example.hauishop;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.hauishop.Entity.KhachHangEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UpdateKhachHangActivity extends AppCompatActivity {

    TextView txtMaKH, txtTenKH, txtDiaChi, txtDienThoai, txtEmail, txtNgaySinh, txtGioiTinh, txtTaiKhoan, txtMatKhau, txtTrangThai;
    EditText edtTenKH, edtDiaChi, edtDienThoai, edtEmail, edtNgaySinh, edtPassCu, edtPassMoi, edtRePassMoi;
    RadioGroup rdoGroup;
    Dialog dialog, dialog1;
    int maKH, maKH1;
    UserLocal userLocal;
    APIServer api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_khach_hang);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        txtMaKH = findViewById(R.id.userMaKH);
        txtTenKH = findViewById(R.id.userTenKH);
        txtDiaChi = findViewById(R.id.userDiaChi);
        txtDienThoai = findViewById(R.id.userDienThoai);
        txtEmail = findViewById(R.id.userMail);
        txtNgaySinh = findViewById(R.id.userNgaySinh);
        txtGioiTinh = findViewById(R.id.userGioiTinh);
        txtTaiKhoan = findViewById(R.id.userTK);
        txtMatKhau = findViewById(R.id.userMatKhau);
        txtTrangThai = findViewById(R.id.userTrangThai);

        getThongTin(api.HostingAPI + api.get_ThongTin_KhachHang);
    }

    private void getThongTin(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        txtMaKH.setText(object.getInt("Ma_khach_hang") + "");
                        txtTenKH.setText(object.getString("Ho_ten_kh"));
                        txtDiaChi.setText(object.getString("Dia_chi"));
                        txtDienThoai.setText(object.getString("Dien_thoai"));
                        txtEmail.setText(object.getString("Email"));
                        txtNgaySinh.setText(object.getString("Ngay_sinh"));
                        txtGioiTinh.setText(object.getString("Gioi_tinh"));
                        txtTaiKhoan.setText(object.getString("Tai_khoan"));
                        txtMatKhau.setText(object.getString("Mat_khau"));
                        txtTrangThai.setText(object.getString("Trang_thai"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateKhachHangActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
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

    private String format(int year, int month, int dayOfMonth) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        return format.format(c.getTime());
    }

    public void xuLyUpdateThongTin(View view) {
        dialog = new Dialog(UpdateKhachHangActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogupdatethongtin);

        edtTenKH = dialog.findViewById(R.id.updateTenKH);
        edtDiaChi = dialog.findViewById(R.id.updateDiaChi);
        edtDienThoai = dialog.findViewById(R.id.updateDienThoai);
        edtEmail = dialog.findViewById(R.id.updateMail);
        edtNgaySinh = dialog.findViewById(R.id.updateNgaySinh);
        rdoGroup = dialog.findViewById(R.id.rdoGroup1);

        Button btnHuy = dialog.findViewById(R.id.btnUpdateHuy);
        Button btnLuu = dialog.findViewById(R.id.btnUpdateLuu);

        edtTenKH.setText(txtTenKH.getText().toString().trim());
        edtDiaChi.setText(txtDiaChi.getText().toString().trim());
        edtDienThoai.setText(txtDienThoai.getText().toString().trim());
        edtEmail.setText(txtEmail.getText().toString().trim());
        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtNgaySinh.setText(format(year, month, dayOfMonth));
                    }
                };
                Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(UpdateKhachHangActivity.this, odsl, y, m, d);
                dialog.show();
            }
        });
        int check;
        if (txtGioiTinh.getText().toString().compareTo("Nam") == 0) {
            check = 0;
        } else {
            check = 1;
        }
        ((RadioButton) rdoGroup.getChildAt(check)).setChecked(true);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTenKH.getText().toString().compareTo("") == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Tên khách hàng không được để trống", Toast.LENGTH_SHORT).show();
                    edtTenKH.requestFocus();
                    return;
                }
                if (edtDiaChi.getText().toString().compareTo("") == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Địa chỉ không được để trống", Toast.LENGTH_SHORT).show();
                    edtDiaChi.requestFocus();
                    return;
                }
                if (edtDienThoai.getText().toString().compareTo("") == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
                    edtDienThoai.requestFocus();
                    return;
                }
                if (edtEmail.getText().toString().compareTo("") == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Email không được để trống", Toast.LENGTH_SHORT).show();
                    edtEmail.requestFocus();
                    return;
                }
                if (edtNgaySinh.getText().toString().compareTo("") == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Ngày sinh không được để trống", Toast.LENGTH_SHORT).show();
                    edtNgaySinh.requestFocus();
                    return;
                }
                UpdateKhachHang(api.HostingAPI + api.update_KhachHang);
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateKhachHangActivity.this);
                builder.setMessage("Bạn có muốn hủy cập nhật không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogA, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public void xuLyDoiMatKhau(View view) {
        dialog1 = new Dialog(UpdateKhachHangActivity.this);
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.dialogdoimatkhau);

        edtPassCu = dialog1.findViewById(R.id.edtPassCu);
        edtPassMoi = dialog1.findViewById(R.id.edtPassMoi);
        edtRePassMoi = dialog1.findViewById(R.id.edtCheckPassMoi);

        Button btnHuy = dialog1.findViewById(R.id.btnDoiPassHuy);
        Button btnLuu = dialog1.findViewById(R.id.btnDoiPassDongY);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateKhachHangActivity.this);
                builder.setMessage("Bạn có muốn hủy cập nhật không?");
                builder.setCancelable(false);
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog1.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPassCu.getText().toString().compareTo("") == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Mật khẩu cũ không được để trống", Toast.LENGTH_SHORT).show();
                    edtPassCu.requestFocus();
                    return;
                }
                if (edtPassMoi.getText().toString().compareTo("") == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Mật khẩu mới không được để trống", Toast.LENGTH_SHORT).show();
                    edtPassMoi.requestFocus();
                    return;
                }
                if (edtRePassMoi.getText().toString().compareTo("") == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Xác nhận mật khẩu mới không được để trống", Toast.LENGTH_SHORT).show();
                    edtRePassMoi.requestFocus();
                    return;
                }
                if (edtPassMoi.getText().toString().compareTo(edtRePassMoi.getText().toString()) != 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Xác nhận mật khẩu chưa trùng khớp", Toast.LENGTH_SHORT).show();
                    edtPassMoi.requestFocus();
                    return;
                }
                if (edtPassMoi.getText().toString().compareTo(edtPassCu.getText().toString()) == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Mật khẩu đang được sử dụng , hãy chọn một mật khẩu khác", Toast.LENGTH_SHORT).show();
                    edtPassMoi.requestFocus();
                    return;
                }
                KiemTraDoiPass(api.HostingAPI + api.kiemtra_DoiMatKhau_KhachHang);
                userLocal.kh.setMatKhau(edtPassMoi + "");
            }
        });
        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public void xuLyDangXuat(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Quay lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Đăng xuất", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userLocal.kh = null;
                startActivity(new Intent(UpdateKhachHangActivity.this, MainActivity.class));
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void UpdateKhachHang(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().compareTo("1") == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    int id = rdoGroup.getCheckedRadioButtonId();
                    String gt = null;
                    switch (id) {
                        case R.id.rdoNamDialog: {
                            gt = "Nam";
                            break;
                        }
                        case R.id.rdoNuDialog: {
                            gt = "Nữ";
                            break;
                        }
                    }
                    startActivity(new Intent(UpdateKhachHangActivity.this, UpdateKhachHangActivity.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateKhachHangActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maKH", txtMaKH.getText().toString().trim());
                map.put("hoTen", edtTenKH.getText().toString().trim());
                map.put("diaChi", edtDiaChi.getText().toString().trim());
                map.put("soDienThoai", edtDienThoai.getText().toString().trim());
                map.put("email", edtEmail.getText().toString().trim());
                map.put("ngaySinh", edtNgaySinh.getText().toString().trim());
                int id = rdoGroup.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rdoNamDialog: {
                        map.put("gioiTinh", "Nam");
                        break;
                    }
                    case R.id.rdoNuDialog: {
                        map.put("gioiTinh", "Nữ");
                        break;
                    }
                }
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void KiemTraDoiPass(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().compareTo("1") == 0) {
                    UpdateMatKhau(api.HostingAPI + api.update_MatKhau_KhachHang);
                } else {
                    Toast.makeText(UpdateKhachHangActivity.this, "Mật khẩu cũ không chính xác , kiểm tra lại.", Toast.LENGTH_SHORT).show();
                    edtPassCu.requestFocus();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateKhachHangActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
                Log.e("er", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maKH", userLocal.getKh().getMaKH() + "");
                map.put("matKhau", edtPassCu.getText().toString().trim());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void UpdateMatKhau(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().compareTo("1") == 0) {
                    Toast.makeText(UpdateKhachHangActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateKhachHangActivity.this, UpdateKhachHangActivity.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateKhachHangActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
                Log.e("er", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maKH", userLocal.getKh().getMaKH() + "");
                map.put("matKhau", edtPassMoi.getText().toString().trim());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateKhachHangActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}
