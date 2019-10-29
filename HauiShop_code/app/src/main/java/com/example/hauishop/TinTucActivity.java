package com.example.hauishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hauishop.Entity.TinTucEntity;

public class TinTucActivity extends AppCompatActivity {

    TextView txtTen, txtNgayDang, txtNoiDung;
    ImageView imgAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_tuc);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        txtTen = findViewById(R.id.txtTinTucTitle);
        txtNgayDang = findViewById(R.id.txttintucNgayLap);
        txtNoiDung = findViewById(R.id.txtTinTucNoiDung);
        imgAnh = findViewById(R.id.imgTintucAnh);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        TinTucEntity tt = (TinTucEntity) bundle.getSerializable("data");
        txtTen.setText(tt.getTenTT());
        txtNoiDung.setText(tt.getNoiDung());
        txtNgayDang.setText(tt.getNgayDang());
        Glide.with(TinTucActivity.this).asBitmap().
                apply(RequestOptions.placeholderOf(R.drawable.avata).error(android.R.drawable.stat_notify_error))
                .load(Base64.decode(tt.getImage(), Base64.DEFAULT)).into(imgAnh);
    }
}
