package com.example.hauishop.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.hauishop.Entity.BannerEntity;
import com.example.hauishop.Entity.GioHangEntity;
import com.example.hauishop.Entity.SanPhamEntity;
import com.example.hauishop.Entity.TinTucEntity;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String Database_Name = "db_shop";
    private static final int Database_Version = 1;
    static SQLiteDatabase sqlDbWrite = null;
    static SQLiteDatabase sqlDbRead = null;
    Context context;

    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, Database_Version);
        this.context = context;
        sqlDbWrite = getWritableDatabase();
        sqlDbRead = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    public void taoBang() {
        String sql = "CREATE TABLE IF not exists san_pham (\n" +
                "    Ma_san_pham  INTEGER,\n" +
                "    Ten_san_pham STRING,\n" +
                "    Thong_tin_sp STRING,\n" +
                "    Gia_nhap     INTEGER,\n" +
                "    Gia_cu       INTEGER,\n" +
                "    Gia_moi      INTEGER,\n" +
                "    Ngay_mo_ban  STRING,\n" +
                "    So_luong     INTEGER,\n" +
                "    Image        STRING,\n" +
                "    Ma_ncc       INTEGER,\n" +
                "    Ma_loai_sp   INTEGER,\n" +
                "    Trang_thai   STRING\n" +
                ");";
        sqlDbWrite.execSQL(sql);
        sql = "CREATE TABLE IF not exists tin_tuc (\n" +
                "    Ma_tin_tuc  INTEGER,\n" +
                "    Ten_tin_tuc STRING,\n" +
                "    Ngay_dang   STRING,\n" +
                "    Noi_dung    STRING,\n" +
                "    Image       STRING );";
        sqlDbWrite.execSQL(sql);
        sql = "CREATE TABLE IF not exists gio_hang (\n" +
                "    Ma_khach_hang INTEGER NOT NULL,\n" +
                "    Ma_san_pham   INTEGER NOT NULL,\n" +
                "    Ten_san_pham  STRING  NOT NULL,\n" +
                "    So_luong      INTEGER NOT NULL,\n" +
                "    Gia_ban       INTEGER NOT NULL,\n" +
                "    Image         STRING  NOT NULL\n" +
                ");";
        sqlDbWrite.execSQL(sql);
        sql = "CREATE TABLE IF not exists loai_san_pham (\n" +
                "    Ma_loai_sp  INTEGER,\n" +
                "    Ten_loai_sp STRING,\n" +
                "    Image       STRING\n" +
                ");";
        sqlDbWrite.execSQL(sql);
        sql = "CREATE TABLE IF not exists banner (" +
                "    Ma_hinh_anh  INTEGER,\n" +
                "    Ten_hinh_anh STRING,\n" +
                "    Image STRING" + ");";
        sqlDbWrite.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertTT(TinTucEntity tt) {
        ContentValues values = new ContentValues();
        values.put("Ma_tin_tuc", tt.getMaTT());
        values.put("Ten_tin_tuc", tt.getTenTT());
        values.put("Ngay_dang", tt.getNgayDang());
        values.put("Noi_dung", tt.getNoiDung());
        values.put("Image", tt.getImage());
        sqlDbWrite.insert("tin_tuc", null, values);
    }

    public void deleteAllTintuc() {
        sqlDbWrite.delete("tin_tuc", null, null);
    }

    public ArrayList<TinTucEntity> getAllTintuc() {
        ArrayList<TinTucEntity> arrTT = new ArrayList<>();
        final Cursor object = sqlDbRead.rawQuery("select * from tin_tuc", null);
        while (object.moveToNext()) {
            TinTucEntity sp = new TinTucEntity(object.getInt(0), object.getString(1),
                    object.getString(2), object.getString(3), object.getString(4));
            arrTT.add(sp);
        }
        object.close();
        return arrTT;
    }

    public void deleteAllGioHang(int maKH) {
        String arrArgs[] = {maKH + ""};
        sqlDbWrite.delete("gio_hang", "Ma_khach_hang = ?", arrArgs);
    }

    public void deleteMotSPGioHang(int maKH, int maSP) {
        String arrArgs[] = {maKH + "", maSP + ""};
        sqlDbWrite.delete("gio_hang", "Ma_khach_hang = ? AND Ma_san_pham = ?", arrArgs);
    }

    public ArrayList<GioHangEntity> getAllGioHang(int maKH) {
        ArrayList<GioHangEntity> arrGH = new ArrayList<>();
        String sql = "select * from gio_hang where Ma_khach_hang = " + maKH;
        final Cursor object = sqlDbRead.rawQuery(sql, null);
        while (object.moveToNext()) {
            GioHangEntity gh = new GioHangEntity(object.getInt(0), object.getInt(1), object.getString(2), object.getInt(3), object.getInt(4), object.getString(5));
            arrGH.add(gh);
        }
        object.close();
        return arrGH;
    }

    public void updateGioHang(int maKH, int maSP, int soLuong) {
        ContentValues values = new ContentValues();
        values.put("So_luong", soLuong);
        String arrArgs[] = {maKH + "", maSP + ""};
        sqlDbWrite.update("gio_hang", values, "Ma_khach_hang = ? AND Ma_san_pham = ?", arrArgs);
    }

    public void insertGH(GioHangEntity gh) {
        ContentValues values = new ContentValues();
        values.put("Ma_khach_hang", gh.getMaKH());
        values.put("Ma_san_pham", gh.getMaSP());
        values.put("Ten_san_pham", gh.getTenSP());
        values.put("So_luong", gh.getSoLuong());
        values.put("Gia_ban", gh.getGiaBan());
        values.put("Image", gh.getImage());
        sqlDbWrite.insert("gio_hang", null, values);
    }

    public void insertBanner(BannerEntity tt) {
        ContentValues values = new ContentValues();
        values.put("Ma_hinh_anh", tt.getMaHA());
        values.put("Ten_hinh_anh", tt.getTenAnh());
        values.put("Image", tt.getImage());
        sqlDbWrite.insert("banner", null, values);
    }

    public void deleteAllBanner() {
        sqlDbWrite.delete("banner", null, null);
    }

    public ArrayList<BannerEntity> getAllBanner() {
        ArrayList<BannerEntity> arrBanner = new ArrayList<>();
        final Cursor object = sqlDbRead.rawQuery("select * from banner", null);
        while (object.moveToNext()) {
            BannerEntity bn = new BannerEntity(object.getInt(0), object.getString(1),
                    object.getString(2));
            arrBanner.add(bn);
        }
        object.close();
        return arrBanner;
    }

    public void insertSP(SanPhamEntity sp) {
        ContentValues values = new ContentValues();
        values.put("Ma_san_pham", sp.getMaSP());
        values.put("Ten_san_pham", sp.getTenSP());
        values.put("Thong_tin_sp", sp.getThongTinSP());
        values.put("Gia_nhap", sp.getGiaNhap());
        values.put("Gia_cu", sp.getGiaCu());
        values.put("Gia_moi", sp.getGiaMoi());
        values.put("Ngay_mo_ban", sp.getNgayMoBan());
        values.put("So_luong", sp.getSoLuong());
        values.put("Image", sp.getImage());
        values.put("Ma_ncc", sp.getMaNCC());
        values.put("Ma_loai_sp", sp.getMaLoaiSP());
        values.put("Trang_thai", sp.getTrangThai());
        sqlDbWrite.insert("san_pham", null, values);
    }

    public void deleteAllSanPham() {
        sqlDbWrite.delete("san_pham", null, null);
    }

    public ArrayList<SanPhamEntity> getAllSanPham() {
        ArrayList<SanPhamEntity> arrSp = new ArrayList<>();
        final Cursor csSanPham = sqlDbRead.rawQuery("select * from san_pham", null);
        while (csSanPham.moveToNext()) {
            SanPhamEntity sp = new SanPhamEntity(csSanPham.getInt(0), csSanPham.getString(1),
                    csSanPham.getString(2), csSanPham.getInt(3),
                    csSanPham.getInt(4), csSanPham.getInt(5), csSanPham.getString(6),
                    csSanPham.getInt(7), csSanPham.getString(8), csSanPham.getInt(9),
                    csSanPham.getInt(10), csSanPham.getString(11));
            arrSp.add(sp);
        }
        csSanPham.close();
        return arrSp;
    }

    public int getSizeSanPham() {
        return sqlDbRead.rawQuery("select * from san_pham", null).getCount();
    }

    public void deleteAllTable() {
        sqlDbWrite.execSQL("drop table san_pham");
        sqlDbWrite.execSQL("drop table loai_san_pham");
        sqlDbWrite.execSQL("drop table tin_tuc");

    }
}
