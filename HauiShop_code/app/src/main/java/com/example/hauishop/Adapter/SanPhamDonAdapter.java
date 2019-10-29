package com.example.hauishop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hauishop.Entity.SanPhamEntity;

import java.util.List;

public class SanPhamDonAdapter extends ArrayAdapter<SanPhamEntity> {

    Context mContext;
    int mLayout;
    List<SanPhamEntity> mList;

    public SanPhamDonAdapter(Context context, int resource, List<SanPhamEntity> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLayout = resource;
        this.mList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (item != null) {
            item = LayoutInflater.from(mContext).inflate(mLayout, null);


        }
        return item;
    }

    private static class ViewHolder {
        ImageView vAnh;
        TextView vTenSP;
        TextView vGiaCu;
        TextView vGiaMoi;
        TextView vThongTin;
    }
}
