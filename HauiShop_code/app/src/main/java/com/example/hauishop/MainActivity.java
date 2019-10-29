package com.example.hauishop;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hauishop.Database.DatabaseHelper;
import com.example.hauishop.ImageLink.ImageLink;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int flag = -1;
    NavigationView navigationView;
    DatabaseHelper dbHelper = null;
    Broadcast broad = new Broadcast();
    public static boolean network = false;
    UserLocal userLocal;
    public static String pattern = "[admin|ADMIN]\\w*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        addControls();
        addEvents();
    }

    private void addEvents() {
    }

    private void addControls() {
        dbHelper = new DatabaseHelper(this);
        dbHelper.taoBang();

        if (userLocal.getKh() != null) {
            View view = navigationView.getHeaderView(0);
            ImageView imgAnh = view.findViewById(R.id.imgAnh);
            TextView txtHoTen = view.findViewById(R.id.txtTen);
            TextView txtEmail = view.findViewById(R.id.txtEmail);
            flag = userLocal.getKh().getMaKH();
            if (userLocal.getKh().getGioiTinh().compareTo("Nam") == 0) {
                imgAnh.setImageResource(ImageLink.getResourceImage(12));
            } else {
                if (userLocal.getKh().getGioiTinh().compareTo("Nữ") == 0) {
                    imgAnh.setImageResource(ImageLink.getResourceImage(11));
                }
            }
            txtHoTen.setText(userLocal.getKh().getTenKH() + "");
            txtEmail.setText(userLocal.getKh().getEmail() + "");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new Fragment_Home();
        fragmentTransaction.add(R.id.frameFragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Bạn có muốn thoát ứng dụng không?");
            builder.setCancelable(false);

            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("quay lại", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogA, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent();
        if (id == R.id.nav_gioHang) {
            startActivity(new Intent(MainActivity.this, GioHangActivity.class));
        } else if (id == R.id.nav_loaiSP) {
            startActivity(new Intent(MainActivity.this, LoaiSanPhamActivity.class));
        } else if (id == R.id.nav_sp) {
            startActivity(new Intent(MainActivity.this, SanPhamActivity.class));
        } else if (id == R.id.nav_bill) {
            startActivity(new Intent(MainActivity.this, HoaDonActivity.class));
        } else if (id == R.id.nav_TaiKhoan) {
            if (flag == -1) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            } else {
                startActivity(new Intent(MainActivity.this, UpdateKhachHangActivity.class));
            }
        } else if (id == R.id.nav_send) {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        } else if (id == R.id.nav_internet) {
            intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_Doanhthu) {
            intent = new Intent(MainActivity.this, DoanhThu.class);
            startActivity(intent);
        } else if (id == R.id.nav_contact) contactSupport();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void contactSupport() {
        Button btnDial, btnCall;
        final RadioGroup gr;
        ConstraintLayout layout;

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_contact);
        dialog.setTitle(getString(R.string.h_tr));
        btnCall = dialog.findViewById(R.id.btnCall);
        btnDial = dialog.findViewById(R.id.btnDial);
        gr = dialog.findViewById(R.id.gr);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNumberChoosen(gr) != -1) {
                    RadioButton rad = dialog.findViewById(gr.getCheckedRadioButtonId());
                    actionSupport(rad.getText().toString(),true);
                } else
                    Toast.makeText(MainActivity.this, "Bạn chưa chọn số diện thoại", Toast.LENGTH_SHORT).show();
            }
        });
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNumberChoosen(gr) != -1) {
                    RadioButton rad = dialog.findViewById(gr.getCheckedRadioButtonId());
                    actionSupport(rad.getText().toString(),false);
                } else
                    Toast.makeText(MainActivity.this, "Bạn chưa chọn số diện thoại", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setOwnerActivity(this);
        dialog.show();
        layout=dialog.findViewById(R.id.layout);
        AnimationDrawable drawable= (AnimationDrawable) layout.getBackground();
        drawable.setEnterFadeDuration(4000);
        drawable.setExitFadeDuration(4000);
        drawable.start();
    }

    private void actionSupport(String s, boolean actionCall) {
        Uri uri = Uri.parse("tel:" + s);
        Intent intent = new Intent(actionCall ? Intent.ACTION_CALL : Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(uri);
        if (actionCall) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
        startActivity(intent);
    }

    private int checkNumberChoosen(RadioGroup gr) {
        return gr.getCheckedRadioButtonId();
    }

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broad, filter);
        super.onResume();
    }

    public class Broadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnectedOrConnecting()) {
                Toast.makeText(context, "Bạn đã trực tuyến", Toast.LENGTH_SHORT).show();
                network = true;
                configFollowNetwork(network);
            } else {
                Toast.makeText(context, "Bạn ở chế độ offline", Toast.LENGTH_SHORT).show();
                network = false;
                configFollowNetwork(network);
            }
        }

        public Broadcast() {
        }
    }


    public void configFollowNetwork(boolean net) {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(net);
        }
        menu.findItem(R.id.nav_internet).setVisible(!net);
        if (!net) menu.findItem(R.id.nav_sp).setVisible(!net);

        if (userLocal.getKh() != null && UserLocal.getKh().getTaiKhoan().matches(pattern)) {
            menu.findItem(R.id.nav_Doanhthu).setVisible(true);
        } else menu.findItem(R.id.nav_Doanhthu).setVisible(false);

    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(broad);
        super.onDestroy();
    }
}

