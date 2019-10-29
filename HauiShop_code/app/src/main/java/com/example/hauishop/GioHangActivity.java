package com.example.hauishop;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.hauishop.Adapter.GioHangAdapter;
import com.example.hauishop.Database.DatabaseHelper;
import com.example.hauishop.Entity.GioHangEntity;
import com.example.hauishop.Entity.KhachHangEntity;
import com.example.hauishop.Entity.SanPhamEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GioHangActivity extends AppCompatActivity implements GioHangAdapter.OnDeleteListener {

    ArrayList<GioHangEntity> arrGio;
    GioHangAdapter gioHangAdapter;
    ListView lvGio;
    APIServer api;
    DatabaseHelper dbHelper = null;
    int soLuong = 0;
    TextView edtSL;
    UserLocal userLocal;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        dbHelper = new DatabaseHelper(this);
        arrGio = new ArrayList<>();
        lvGio = findViewById(R.id.lvGio);

        gioHangAdapter = new GioHangAdapter(GioHangActivity.this, R.layout.item_giohang, arrGio, GioHangActivity.this);
        lvGio.setAdapter(gioHangAdapter);
        if (userLocal.kh == null) {
            Toast.makeText(this, "Hãy đăng nhập để tiếp tục", Toast.LENGTH_SHORT).show();
        } else {
            //GetGioHang(api.HostingAPI + api.get_GioHang);
            arrGio.addAll(dbHelper.getAllGioHang(userLocal.kh.getMaKH()));
        }
    }

    private void GetGioHang(String url) {
        arrGio.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        arrGio.add(new GioHangEntity(object.getInt("Ma_khach_hang"), object.getInt("Ma_san_pham"), object.getString("Ten_san_pham"), object.getInt("So_luong"), object.getInt("Gia_ban"), object.getString("Image")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                gioHangAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GioHangActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
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

    private void Delete_SP_TrongGio(String url, final int maSP) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(GioHangActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GioHangActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
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
    public void onClickDelete(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Bạn muốn xóa sản phẩm " + arrGio.get(i).getTenSP() + " ?");
        builder.setIcon(R.drawable.ic_delete);
        builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Delete_SP_TrongGio(api.HostingAPI + api.delete_SP_TrongGio, arrGio.get(i).getMaSP());
                dbHelper.deleteMotSPGioHang(userLocal.kh.getMaKH(),arrGio.get(i).getMaSP());
                arrGio.remove(i);
                gioHangAdapter.notifyDataSetChanged();
            }
        });
        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClickUpdate(final int i) {
        final ArrayList<SanPhamEntity> arrSP = new ArrayList<>();
        final Dialog dialog = new Dialog(GioHangActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_update_soluong);

        final ImageView imgAnh = dialog.findViewById(R.id.soLuongAnh);
        final TextView txtTen = dialog.findViewById(R.id.soLuongTxtTenSP);
        final TextView txtGiaCu = dialog.findViewById(R.id.soLuongGiaCu);
        final TextView txtGiaMoi = dialog.findViewById(R.id.soLuongGiaMoi);
        final TextView txtThongTin = dialog.findViewById(R.id.soLuongThongTin);
        edtSL = dialog.findViewById(R.id.edtSL);
        Button btnUpdate = dialog.findViewById(R.id.btnUpSoLuong);
        Button btnHuy = dialog.findViewById(R.id.btnUpHuy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, api.HostingAPI + api.get_SanPham_MaSP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        arrSP.add(new SanPhamEntity(object.getInt("Ma_sp"), object.getString("Ten_sp"), object.getString("Thong_tin_sp"), object.getInt("Gia_nhap"), object.getInt("Gia_cu"), object.getInt("Gia_moi"), object.getString("Ngay_mo_ban"), object.getInt("So_luong"), object.getString("Image"), object.getInt("Ma_ncc"), object.getInt("Ma_loai_sp"), object.getString("Trang_thai")));
                    }
                    Glide.with(GioHangActivity.this).asBitmap().load(Base64.decode(arrSP.get(0).getImage(), Base64.DEFAULT)).into(imgAnh);
                    txtTen.setText(arrSP.get(0).getTenSP());
                    txtGiaCu.setText(arrSP.get(0).getGiaCu() + "");
                    txtGiaMoi.setText(arrSP.get(0).getGiaMoi() + "");
                    txtThongTin.setText(arrSP.get(0).getThongTinSP());
                    edtSL.setText(arrGio.get(i).getSoLuong()+"");
                    //GetSoLuong_TrongGio(api.HostingAPI + api.get_SoLuong_TrongGio_TheoMaSP, userLocal.getKh().getMaKH(), arrSP.get(0).getMaSP());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GioHangActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maSP", arrGio.get(i).getMaSP() + "");
                return map;
            }
        };
        requestQueue.add(stringRequest);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSL.getText().toString().trim().compareTo("") == 0) {
                    Toast.makeText(GioHangActivity.this, "Số lượng không được để trống", Toast.LENGTH_SHORT).show();
                    edtSL.requestFocus();
                    return;
                }
                //Update_SoLuong(api.HostingAPI + api.update_SP_TrongGio, arrGio.get(i).getMaKH(), arrGio.get(i).getMaSP(), Integer.parseInt(edtSL.getText().toString().trim()));
                dbHelper.updateGioHang(userLocal.kh.getMaKH(),arrGio.get(i).getMaSP(),Integer.parseInt(edtSL.getText().toString()));
                arrGio.get(i).setSoLuong(Integer.parseInt(edtSL.getText().toString().trim()));
                gioHangAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    private void Update_SoLuong(String url, final int maKH, final int maSP, final int soLuong) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.compareTo("1") == 0) {
                    Toast.makeText(GioHangActivity.this, "Cập nhật số lượng sản phẩm thành công", Toast.LENGTH_SHORT).show();
                }
                gioHangAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GioHangActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("maKH", maKH + "");
                map.put("maSP", maSP + "");
                map.put("soLuong", soLuong + "");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetSoLuong_TrongGio(String url, final int maKH, final int maSP) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        edtSL.setText(object.getInt("So_luong") + "");
                    }
                    gioHangAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GioHangActivity.this, "Kiểm tra lại đường truyền internet", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(GioHangActivity.this, MainActivity.class));
        super.onBackPressed();
    }

    public void xuLyThanhToan(View view) {
        if (arrGio.size() == 0) {
            Toast.makeText(this, "Chưa có sản phẩm nào trong giỏ", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.deleteAllGioHang(userLocal.kh.getMaKH());
            Intent intent = new Intent();
            intent.setClass(GioHangActivity.this, ThanhToanActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("data1", arrGio);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }

    }

    public void xuLyMinus(View view) {
        int d = Integer.parseInt(edtSL.getText().toString().trim());
        if (d == 1) {
            Toast.makeText(this, "Không thể giảm thêm số lượng sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        d--;
        soLuong = d;
        edtSL.setText(d + "");
    }

    public void xuLyPlus(View view) {
        int d = Integer.parseInt(edtSL.getText().toString().trim());
        d++;
        soLuong = d;
        edtSL.setText(d + "");
    }
}
