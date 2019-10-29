package com.example.hauishop.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hauishop.Entity.LoaiSanPhamEntity;
import com.example.hauishop.R;

import java.util.ArrayList;

public class LoaiSPAdapter extends RecyclerView.Adapter<LoaiSPAdapter.ViewHolder> {

    private ArrayList<LoaiSanPhamEntity> arrLoaiSP;
    private Context mContext;
    private OnNoteListener onNoteListener;

    public LoaiSPAdapter(ArrayList<LoaiSanPhamEntity> arrLoaiSP, Context mContext, OnNoteListener onNoteListener) {
        this.arrLoaiSP = arrLoaiSP;
        this.mContext = mContext;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_loai_sp, viewGroup, false);
        ViewHolder holder = new ViewHolder(v, onNoteListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).asBitmap().load(Base64.decode(arrLoaiSP.get(i).getImgAnh(), Base64.DEFAULT)).into(viewHolder.vAnh);
        viewHolder.vTenLoaiSP.setText(arrLoaiSP.get(i).getTenLoaiSP());
    }

    @Override
    public int getItemCount() {
        return arrLoaiSP.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView vAnh;
        TextView vTenLoaiSP;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.vAnh = itemView.findViewById(R.id.itemImgAnh);
            this.vTenLoaiSP = itemView.findViewById(R.id.itemTxtTenLoaiSP);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int i);
    }
}
