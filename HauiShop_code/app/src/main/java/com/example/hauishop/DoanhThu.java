package com.example.hauishop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hauishop.API.APIServer;
import com.example.hauishop.Entity.DoanhThuEntity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DoanhThu extends AppCompatActivity {
    static BarChart barChart = null;
    static BarDataSet dataSet = null;
    static BarEntry entry = null;
    static ArrayList<DoanhThuEntity> arrDT = null;
    public static BarData barData=null;
    public TextView textView;
    static PieChart pieChart=null;
    PieDataSet pieDataSet=null;
    PieEntry pieEntry=null;
    PieData pieData=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);
        initView();
    }

    private void initView() {
        barChart = findViewById(R.id.barChart);
        pieChart=findViewById(R.id.pieChart);
        arrDT = new ArrayList<>();
        textView=findViewById(R.id.txtMax);

        //show bar chart by default
        showDoanhThu(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_doanhthu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_Bar:
                showDoanhThu(true);
                break;
            case R.id.menu_Pie:
                showDoanhThu(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDoanhThuWithPie() {
    }

    public void showDoanhThu(final boolean bar) {
        arrDT.clear();
        final String url = APIServer.HostingAPI + APIServer.getDoanhthu;
        RequestQueue queue = Volley.newRequestQueue(this);
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int length = response.length();
                try {
                    for (int i = 0; i < length; i++) {
                        JSONObject o = response.getJSONObject(i);
                        DoanhThuEntity dtEntity = new DoanhThuEntity(o.getInt("ma"), o.getInt("sl"),
                                o.getInt("tongtien"), o.getString("ngay"));
                        if (!arrDT.contains(dtEntity)) {
                            arrDT.add(dtEntity);
                        } else {
                            int index = arrDT.indexOf(dtEntity);
                            tinhTongTienvaSoLuong(index, dtEntity);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (bar)bindBarData();
                else {
                    bindPieData();
                }

                DoanhThuEntity max=Collections.max(arrDT, new Comparator<DoanhThuEntity>() {
                    @Override
                    public int compare(DoanhThuEntity o1, DoanhThuEntity o2) {
                        return o1.tongSoLuong>o2.tongSoLuong?0:-1;
                    }
                });
                String s=max.ngayLap;
                textView.setText("Giá trị lớn nhất: tháng " + s.substring(s.indexOf("-")+1,s.lastIndexOf("-"))
                        +" - " + max.tongSoLuong+" sản phẩm");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    public void tinhTongTienvaSoLuong(int index, DoanhThuEntity duplicatedObject) {
        DoanhThuEntity dt = arrDT.get(index);
        dt.tongSoLuong += duplicatedObject.tongSoLuong;
        dt.tongTien += duplicatedObject.tongTien;
    }

    public void bindBarData(){
        ArrayList<BarEntry> arrData = new ArrayList<>();
        for (DoanhThuEntity e : arrDT) {
            String s = e.ngayLap;
            arrData.add(new BarEntry(Integer.parseInt(s.substring(s.indexOf("-") + 1, s.lastIndexOf("-"))),
                    e.tongSoLuong));
        }
        dataSet = new BarDataSet(arrData, "Số lương sản phẩm đã bán");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barData=new BarData(dataSet);
        barChart.animateY(1500);
        barChart.setData(barData);
        barChart.setVisibility(View.VISIBLE);
        barChart.setFitBars(true);
        barChart.setScaleEnabled(false);
        pieChart.setVisibility(View.INVISIBLE);
    }
    public void bindPieData(){
        ArrayList<PieEntry> arrData = new ArrayList<>();
        for (DoanhThuEntity e : arrDT) {
            String s = e.ngayLap;
            arrData.add(new PieEntry(e.tongSoLuong,
                    "T"+Integer.parseInt(s.substring(s.indexOf("-") + 1, s.lastIndexOf("-")))));
        }
        pieDataSet = new PieDataSet(arrData, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData=new PieData(pieDataSet);
        pieChart.animateX(1500);
        pieChart.setData(pieData);
        pieChart.setCenterText("doanh số sp đã bán");
        pieChart.setCenterTextSize(R.dimen.s20);
        pieChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.INVISIBLE);
    }
}
