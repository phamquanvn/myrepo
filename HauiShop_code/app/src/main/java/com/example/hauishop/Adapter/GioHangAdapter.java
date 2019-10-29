package com.example.hauishop.Adapter;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hauishop.API.APIServer;
import com.example.hauishop.Entity.GioHangEntity;
import com.example.hauishop.R;

import java.util.List;

public class GioHangAdapter extends ArrayAdapter<GioHangEntity> {

    Context mContext;
    int mLayout;
    List<GioHangEntity> mList;
    OnDeleteListener onDeleteListener;
    APIServer api;


    public GioHangAdapter(Context context, int resource, List<GioHangEntity> objects, OnDeleteListener onDeleteListener) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLayout = resource;
        this.mList = objects;
        this.onDeleteListener = onDeleteListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (item == null) {
            item = LayoutInflater.from(mContext).inflate(mLayout, null);

            viewHolder.vAnh = item.findViewById(R.id.itemAnhGio);
            viewHolder.vTenSP = item.findViewById(R.id.itemTenSPGio);
            viewHolder.vGia = item.findViewById(R.id.itemGiaBanGio);
            viewHolder.vSoLuong = item.findViewById(R.id.itemSoLuongGio);
            viewHolder.vDelete = item.findViewById(R.id.itemDeleteGio);
            viewHolder.vUpdate = item.findViewById(R.id.itemUpdateGio);

            item.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) item.getTag();
        }

        Glide.with(mContext).asBitmap().load(Base64.decode(mList.get(position).getImage(), Base64.DEFAULT)).into(viewHolder.vAnh);
        viewHolder.vTenSP.setText(mList.get(position).getTenSP());
        viewHolder.vGia.setText(mList.get(position).getGiaBan() + "");
        viewHolder.vSoLuong.setText(mList.get(position).getSoLuong() + "");
        viewHolder.vDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteListener.onClickDelete(position);
            }
        });
        viewHolder.vUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteListener.onClickUpdate(position);
            }
        });

        return item;
    }

    public static class ViewHolder {
        ImageView vAnh;
        TextView vTenSP;
        TextView vGia;
        TextView vSoLuong;
        ImageView vDelete;
        ImageView vUpdate;
    }

    public interface OnDeleteListener {
        void onClickDelete(int i);
        void onClickUpdate(int i);
    }
}
