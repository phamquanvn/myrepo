package com.example.hauishop;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.hauishop.API.APIServer;
import com.example.hauishop.Database.DatabaseHelper;
import com.example.hauishop.Entity.GioHangEntity;
import com.example.hauishop.Entity.SanPhamEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SanPhamDonActivity extends AppCompatActivity {

    TextView txtGiaCu, txtGiaMoi, txtThongTin;
    Button btnThemHang;
    ImageView imgAnh;
    EditText edtSoLuong;
    UserLocal userLocal;
    ArrayList<SanPhamEntity> arrSP;
    SanPhamEntity sp;
    DatabaseHelper dbHelper = null;
    ArrayList<GioHangEntity> arrGio;
    APIServer api;
    int flag = 0;
    ActionBar aBar;
    Toolbar tbSpDon;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_don);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnThemHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.network = false) {
                    Toast.makeText(SanPhamDonActivity.this, "Bạn đang offline, hãy kiểm tra kết nối internet!!", Toast.LENGTH_SHORT).show();
                }
                if (userLocal.kh == null) {
                    Toast.makeText(SanPhamDonActivity.this, "Hãy đăng nhập để tiếp tục", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SanPhamDonActivity.this, LoginActivity.class));
                } else {
                    if (edtSoLuong.getText().toString().trim().compareTo("") == 0) {
                        Toast.makeText(SanPhamDonActivity.this, "Số lượng không được để trống", Toast.LENGTH_SHORT).show();
                        edtSoLuong.requestFocus();
                        return;
                    }
                    //KtraSP_TrongGio(api.HostingAPI + api.kiemtra_SP_TrongGio, userLocal.getKh().getMaKH(), sp.getMaSP());
                    if (arrGio.contains(sp.getMaSP())) {
                        Toast.makeText(SanPhamDonActivity.this, "Sản phẩm đã có trong giỏ hàng, quý khách có thể thay đổi số lượng nếu muốn mua thêm", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SanPhamDonActivity.this, "Thêm hàng vào giỏ thành công", Toast.LENGTH_SHORT).show();
                        GioHangEntity gh = new GioHangEntity(userLocal.kh.getMaKH(), sp.getMaSP(), sp.getTenSP(), Integer.parseInt(edtSoLuong.getText().toString()), sp.getGiaMoi(), sp.getImage());
                        dbHelper.insertGH(gh);
                        edtSoLuong.setText("");
                        startActivity(new Intent(SanPhamDonActivity.this, SanPhamActivity.class));
                    }

                }
            }
        });
    }


    private void addControls() {
        arrGio = new ArrayList<>();
        dbHelper = new DatabaseHelper(this);
        if (userLocal!=null)
        arrGio.addAll(dbHelper.getAllGioHang(userLocal.kh.getMaKH()));

        tbSpDon = findViewById(R.id.tbSanPhamDon);
        setSupportActionBar(tbSpDon);
        aBar=getSupportActionBar();

        txtGiaCu = findViewById(R.id.spDonGiaCu);
        txtGiaMoi = findViewById(R.id.spDonGiaMoi);
        txtThongTin = findViewById(R.id.txtspDonThongTin);
        btnThemHang = findViewById(R.id.btnspDonThemGio);
        imgAnh = findViewById(R.id.spDonAnh);
        edtSoLuong = findViewById(R.id.edtspDonSL);
        edtSoLuong.clearFocus();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            sp = (SanPhamEntity) bundle.getSerializable("data");
            Glide.with(SanPhamDonActivity.this).asBitmap().load(Base64.decode(sp.getImage(), Base64.DEFAULT)).into(imgAnh);
            txtGiaCu.setText(sp.getGiaCu() + "");
            txtGiaCu.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            txtGiaMoi.setText(sp.getGiaMoi() + "");
            aBar.setTitle(sp.getTenSP());
            txtThongTin.setText(sp.getThongTinSP()+getString(R.string.dummy_tex));

            flag = bundle.getInt("flag");
        }
    }

    private void InsertGioHang(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.compareTo("1") == 0) {
                    Toast.makeText(SanPhamDonActivity.this, "Thêm hàng vào giỏ thành công", Toast.LENGTH_SHORT).show();
                    edtSoLuong.setText("");
                    startActivity(new Intent(SanPhamDonActivity.this, SanPhamActivity.class));
                } else {
                    Toast.makeText(SanPhamDonActivity.this, "Thêm hàng vào giỏ thất bại ", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SanPhamDonActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maKH", userLocal.getKh().getMaKH() + "");
                map.put("maSP", sp.getMaSP() + "");
                map.put("tenSP", sp.getTenSP());
                map.put("soLuong", edtSoLuong.getText().toString().trim());
                map.put("giaBan", sp.getGiaMoi() + "");
                map.put("image", sp.getImage());
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void KtraSP_TrongGio(String url, final int maKH, final int maSP) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.compareTo("1") == 0) {
                    Toast.makeText(SanPhamDonActivity.this, "Sản phẩm đã có trong giỏ hàng, quý khách có thể thay đổi số lượng nếu muốn mua thêm", Toast.LENGTH_SHORT).show();
                } else {
                    InsertGioHang(api.HostingAPI + api.insert_GioHang);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SanPhamDonActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maKH", maKH + "");
                map.put("maSP", maSP + "");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (flag == 1) {
            intent.setClass(SanPhamDonActivity.this, SanPhamTheoLoaiActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("data", sp.getMaLoaiSP());
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        } else {
            if (flag == 2) {
                intent.setClass(SanPhamDonActivity.this, SanPhamActivity.class);
                startActivity(intent);
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return true;
    }
}
