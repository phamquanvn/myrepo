package com.example.hauishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hauishop.API.APIServer;
import com.example.hauishop.Adapter.SanPhamAdapter;
import com.example.hauishop.Database.DatabaseHelper;
import com.example.hauishop.Entity.KhachHangEntity;
import com.example.hauishop.Entity.SanPhamEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SanPhamActivity extends AppCompatActivity implements SanPhamAdapter.OnNoteListener {

    RecyclerView mRv1;
    ArrayList<SanPhamEntity> arrSP;
    SanPhamAdapter sanPhamAdapter;
    APIServer api;
    DatabaseHelper dbHelper = null;
    ArrayList<SanPhamEntity> arrSP1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

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
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void addEvents() {
    }

    private void addControls() {
        dbHelper = new DatabaseHelper(this);
        arrSP = new ArrayList<>();
        mRv1 = findViewById(R.id.rvSP);
        mRv1.setLayoutManager(new GridLayoutManager(SanPhamActivity.this, 2));
        sanPhamAdapter = new SanPhamAdapter(arrSP, SanPhamActivity.this, SanPhamActivity.this);
        mRv1.setAdapter(sanPhamAdapter);
        GetSanPham(api.HostingAPI + api.get_SanPham);
    }


    private void GetSanPham(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        SanPhamEntity sp = new SanPhamEntity(object.getInt("Ma_sp"), object.getString("Ten_sp"),
                                object.getString("Thong_tin_sp"), object.getInt("Gia_nhap"),
                                object.getInt("Gia_cu"), object.getInt("Gia_moi"), object.getString("Ngay_mo_ban"),
                                object.getInt("So_luong"), object.getString("Image"), object.getInt("Ma_ncc"),
                                object.getInt("Ma_loai_sp"), object.getString("Trang_thai"));
                        arrSP.add(sp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                sanPhamAdapter = new SanPhamAdapter(arrSP, SanPhamActivity.this, SanPhamActivity.this);
                mRv1.setAdapter(sanPhamAdapter);
                MainActivity.network = true;
                sanPhamAdapter.notifyDataSetChanged();
                dbHelper.deleteAllSanPham();
                for (SanPhamEntity s : arrSP) dbHelper.insertSP(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MainActivity.network = false;
                Toast.makeText(SanPhamActivity.this, "Bạn ở chế dộ ở chế đọ offline", Toast.LENGTH_SHORT).show();
                arrSP.clear();
                arrSP.addAll(dbHelper.getAllSanPham());
                sanPhamAdapter.notifyDataSetChanged();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetSP_TheoMa(String url, final int maSP) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setClass(SanPhamActivity.this, SanPhamDonActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("data", arrSP.get(0));
                bundle.putInt("flag", 2);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maSP", maSP + "");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onNoteClick(final int i) {
        if (MainActivity.network == true) {
            SanPhamEntity sp = arrSP.get(i);
            GetSP_TheoMa(api.HostingAPI + api.get_SanPham_MaSP, sp.getMaSP());
        } else {
            Toast.makeText(SanPhamActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SanPhamActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}
