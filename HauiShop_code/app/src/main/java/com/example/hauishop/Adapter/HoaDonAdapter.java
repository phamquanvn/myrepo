package com.example.hauishop.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hauishop.Entity.HoaDonEntity;
import com.example.hauishop.R;

import java.util.List;

public class HoaDonAdapter extends ArrayAdapter<HoaDonEntity> {

    Context mContext;
    int mLayout;
    List<HoaDonEntity> mList;

    public HoaDonAdapter(Context context, int resource, List<HoaDonEntity> objects) {
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

            viewHolder.vMaHD = item.findViewById(R.id.itemHDMaHD);
            viewHolder.vTong = item.findViewById(R.id.itemHDTong);
            viewHolder.vNgayLap = item.findViewById(R.id.itemHDNgayLap);
            viewHolder.vTrangThai = item.findViewById(R.id.itemHDSTT);

            item.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) item.getTag();
        }
        viewHolder.vMaHD.setText(mList.get(position).getMaHD() + "");
        viewHolder.vTong.setText(mList.get(position).getTongTien() + "");
        viewHolder.vNgayLap.setText(mList.get(position).getNgayLap() + "");
        if (mList.get(position).getTrangThai().compareTo("Chờ") == 0) {
            viewHolder.vTrangThai.setText("Hóa đơn đang được xử lý");
            viewHolder.vTrangThai.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
        } else if (mList.get(position).getTrangThai().compareTo("Xong") == 0) {
            viewHolder.vTrangThai.setText("Tiếp nhận hóa đơn thành công");
            viewHolder.vTrangThai.setTextColor(ContextCompat.getColor(mContext, R.color.colorBlue));
        }
        return item;
    }

    public static class ViewHolder {
        TextView vMaHD;
        TextView vTong;
        TextView vNgayLap;
        TextView vTrangThai;
    }
}
