package com.example.hauishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.hauishop.Adapter.LoaiSPAdapter;
import com.example.hauishop.Entity.KhachHangEntity;
import com.example.hauishop.Entity.LoaiSanPhamEntity;
import com.example.hauishop.Entity.SanPhamEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoaiSanPhamActivity extends AppCompatActivity implements LoaiSPAdapter.OnNoteListener {

    RecyclerView mRv;
    ArrayList<LoaiSanPhamEntity> arrLoaiSP;
    LoaiSPAdapter loaiSPAdapter;
    APIServer api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_san_pham);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        arrLoaiSP = new ArrayList<>();
        mRv = findViewById(R.id.rvLoaiSP);
        mRv.setLayoutManager(new GridLayoutManager(LoaiSanPhamActivity.this, 3));

        loaiSPAdapter = new LoaiSPAdapter(arrLoaiSP, LoaiSanPhamActivity.this, LoaiSanPhamActivity.this);
        mRv.setAdapter(loaiSPAdapter);
        GetLoaiSP(api.HostingAPI + api.get_LoaiSP);
    }

    private void GetLoaiSP(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        arrLoaiSP.add(new LoaiSanPhamEntity(object.getInt("Ma_loai_sp"), object.getString("Ten_loai_sp"), object.getString("Image")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                loaiSPAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoaiSanPhamActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onNoteClick(final int i) {
        LoaiSanPhamEntity lsp = arrLoaiSP.get(i);
        Intent intent = new Intent();
        intent.setClass(LoaiSanPhamActivity.this, SanPhamTheoLoaiActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("data", lsp.getMaLoaiSP());
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoaiSanPhamActivity.this, MainActivity.class));
        super.onBackPressed();
    }
}
