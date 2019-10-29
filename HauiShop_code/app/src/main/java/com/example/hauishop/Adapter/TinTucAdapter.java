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
import com.example.hauishop.Entity.TinTucEntity;
import com.example.hauishop.R;

import java.util.List;

public class TinTucAdapter extends ArrayAdapter<TinTucEntity> {

    Context mContext;
    int mLayout;
    List<TinTucEntity> mList;

    public TinTucAdapter(Context context, int resource, List<TinTucEntity> objects) {
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

            viewHolder.vTenTT = item.findViewById(R.id.itemTinTucTen);
            viewHolder.vNgayLap = item.findViewById(R.id.itemTintucNgayLap);
            viewHolder.vAnh = item.findViewById(R.id.itemTinTucAnh);

            item.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) item.getTag();
        }
        viewHolder.vTenTT.setText(mList.get(position).getTenTT());
        viewHolder.vNgayLap.setText(mList.get(position).getNgayDang());
        Glide.with(mContext).asBitmap().load(Base64.decode(mList.get(position)
                .getImage(), Base64.DEFAULT)).into(viewHolder.vAnh);
        return item;
    }

    public static class ViewHolder {
        TextView vTenTT;
        TextView vNgayLap;
        ImageView vAnh;
    }
}
