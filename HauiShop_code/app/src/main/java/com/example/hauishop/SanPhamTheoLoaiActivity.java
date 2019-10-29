package com.example.hauishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hauishop.API.APIServer;
import com.example.hauishop.Adapter.SanPhamAdapter;
import com.example.hauishop.Entity.KhachHangEntity;
import com.example.hauishop.Entity.SanPhamEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SanPhamTheoLoaiActivity extends AppCompatActivity implements SanPhamAdapter.OnNoteListener {

    RecyclerView mRv2;
    ArrayList<SanPhamEntity> arrSP;
    ArrayList<SanPhamEntity> arrSP1;
    SanPhamAdapter sanPhamAdapter;
    APIServer api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_theo_loai);

        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_san_pham, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                sanPhamAdapter.filter(s.trim());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void addEvents() {
    }

    private void addControls() {
        arrSP1=new ArrayList<>();
        arrSP = new ArrayList<>();
        mRv2 = findViewById(R.id.rvSPTheoLoai);
        mRv2.setLayoutManager(new GridLayoutManager(SanPhamTheoLoaiActivity.this, 2));

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            int maLoaiSP = bundle.getInt("data");
            GetSanPham_TheoLoai(api.HostingAPI + api.get_SanPham_LoaiSP, maLoaiSP);
        }
    }

    @Override
    public void onNoteClick(int i) {
        Intent intent = new Intent();
        intent.setClass(SanPhamTheoLoaiActivity.this, SanPhamDonActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("data", arrSP.get(i));
        bundle.putInt("flag", 1);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SanPhamTheoLoaiActivity.this, LoaiSanPhamActivity.class));
        super.onBackPressed();
    }

    private void GetSanPham_TheoLoai(String url, final int maLoai) {
        arrSP.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        arrSP.add(new SanPhamEntity(object.getInt("Ma_sp"), object.getString("Ten_sp"), object.getString("Thong_tin_sp"), object.getInt("Gia_nhap"), object.getInt("Gia_cu"), object.getInt("Gia_moi"), object.getString("Ngay_mo_ban"), object.getInt("So_luong"), object.getString("Image"), object.getInt("Ma_ncc"), object.getInt("Ma_loai_sp"), object.getString("Trang_thai")));
                    }
                    sanPhamAdapter = new SanPhamAdapter(arrSP, SanPhamTheoLoaiActivity.this, SanPhamTheoLoaiActivity.this);
                    mRv2.setAdapter(sanPhamAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SanPhamTheoLoaiActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maLoaiSP", maLoai + "");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}
