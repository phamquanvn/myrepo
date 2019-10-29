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
import com.example.hauishop.Entity.CTHoaDonEntity;
import com.example.hauishop.R;

import java.util.List;

public class CT_HoaDon_Adapter extends ArrayAdapter<CTHoaDonEntity> {

    Context mContext;
    int mLayout;
    List<CTHoaDonEntity> mList;

    public CT_HoaDon_Adapter(Context context, int resource, List<CTHoaDonEntity> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mLayout = resource;
        this.mList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (item == null) {
            item = LayoutInflater.from(mContext).inflate(mLayout, null);

            viewHolder.vAnh = item.findViewById(R.id.itemAnhHD);
            viewHolder.vTenSP = item.findViewById(R.id.itemTenSPHD);
            viewHolder.vGia = item.findViewById(R.id.itemGiaBanHD);
            viewHolder.vSoLuong = item.findViewById(R.id.itemSoLuongHD);

            item.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) item.getTag();
        }

        Glide.with(mContext).asBitmap().load(Base64.decode(mList.get(position).getImage(), Base64.DEFAULT)).into(viewHolder.vAnh);
        viewHolder.vTenSP.setText(mList.get(position).getTenSP());
        viewHolder.vGia.setText(mList.get(position).getGiaBan() + "");
        viewHolder.vSoLuong.setText(mList.get(position).getSoLuong() + "");

        return item;
    }

    public static class ViewHolder {
        ImageView vAnh;
        TextView vTenSP;
        TextView vGia;
        TextView vSoLuong;
        TextView vTrangThai;
    }
}
