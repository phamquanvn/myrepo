package com.example.hauishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hauishop.API.APIServer;
import com.example.hauishop.Adapter.TinTucAdapter;
import com.example.hauishop.Adapter.ViewPagerAdapter;
import com.example.hauishop.Database.DatabaseHelper;
import com.example.hauishop.Entity.BannerEntity;
import com.example.hauishop.Entity.TinTucEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_Home extends Fragment {

    View view;
    ViewPager viewPager;
    LinearLayout sliderDot;
    private int dotcount;
    private ImageView[] dots;
    ViewPagerAdapter adapter;
    ArrayList<BannerEntity> arrBaner;
    APIServer api;
    ListView lvTinTuc;
    ArrayList<TinTucEntity> arrTT;
    TinTucAdapter tinTucAdapter;
    DatabaseHelper dbHelper=null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        addControls();
        addEvents();
        return view;
    }

    private void addEvents() {

    }

    private void addControls() {
        dbHelper=new DatabaseHelper(getActivity());
        arrBaner = new ArrayList<>();
        viewPager = view.findViewById(R.id.viewPager);
        sliderDot = view.findViewById(R.id.sliderDot);
        arrTT = new ArrayList<>();
        lvTinTuc = view.findViewById(R.id.lvTinTuc);

        tinTucAdapter = new TinTucAdapter(getContext(), R.layout.item_tin_tuc, arrTT);
        lvTinTuc.setAdapter(tinTucAdapter);

        adapter = new ViewPagerAdapter(view.getContext(), arrBaner);
        viewPager.setAdapter(adapter);
        Get_Banner(api.HostingAPI + api.get_Banner);
        GetTinTuc(api.HostingAPI + api.get_TinTuc);

    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == adapter.getCount() - 1) {
                        viewPager.setCurrentItem(0);
                    } else {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                }
            });
        }
    }

    private void Get_Banner(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        arrBaner.add(new BannerEntity(object.getInt("Ma_ha"), object.getString("Ten_anh"),
                                object.getString("Image")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                dbHelper.deleteAllBanner();
                for(BannerEntity bn:arrBaner) dbHelper.insertBanner(bn);
                adapter.notifyDataSetChanged();
                animateBanner();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), "Bạn ở chế độ offline", Toast.LENGTH_SHORT).show();
                arrBaner.clear();
                arrBaner.addAll(dbHelper.getAllBanner());
                adapter.notifyDataSetChanged();
                animateBanner();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void animateBanner() {
        dotcount = adapter.getCount();
        dots = new ImageView[dotcount];

        for (int i = 0; i < dotcount; i++) {
            dots[i] = new ImageView(view.getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDot.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int po) {
                for (int i = 0; i < dotcount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.nonactive_dot));
                }
                dots[po].setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 3000, 4000);
    }

    private void GetTinTuc(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        arrTT.add(new TinTucEntity(object.getInt("Ma_tin_tuc"), object.getString("Ten_tin_tuc"),
                                object.getString("Ngay_dang"),
                                object.getString("Noi_dung"), object.optString("Image")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                MainActivity.network=true;
                tinTucAdapter.notifyDataSetChanged();
                dbHelper.deleteAllTintuc();
                for(TinTucEntity tt:arrTT) dbHelper.insertTT(tt);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MainActivity.network=false;
                Toast.makeText(getActivity(), "Bạn ở chế độ offline", Toast.LENGTH_SHORT).show();
                arrTT.clear();
                arrTT.addAll(dbHelper.getAllTintuc());
                tinTucAdapter.notifyDataSetChanged();
            }
        });
        requestQueue.add(jsonArrayRequest);
        lvTinTuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TinTucEntity tt = arrTT.get(position);
                Intent intent = new Intent(getActivity(), TinTucActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", tt);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
    }
}
