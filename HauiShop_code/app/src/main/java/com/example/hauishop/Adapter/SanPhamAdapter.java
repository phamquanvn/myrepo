package com.example.hauishop.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hauishop.Database.DatabaseHelper;
import com.example.hauishop.Entity.SanPhamEntity;
import com.example.hauishop.MainActivity;
import com.example.hauishop.R;
import com.example.hauishop.SanPhamActivity;

import java.util.ArrayList;
import java.util.Locale;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private ArrayList<SanPhamEntity> arrSP;
    private ArrayList<SanPhamEntity> arrPhu;
    private Context mContext;
    private OnNoteListener onNoteListener;
    private DatabaseHelper db;

    public SanPhamAdapter(ArrayList<SanPhamEntity> arrSP, Context mContext, OnNoteListener onNoteListener) {
        this.arrSP = arrSP;
        this.mContext = mContext;
        this.onNoteListener = onNoteListener;
        this.arrPhu = new ArrayList<>();
        this.arrPhu.addAll(arrSP);
        this.db = new DatabaseHelper(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_san_pham, viewGroup, false);
        ViewHolder holder = new ViewHolder(v, onNoteListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).asBitmap().load(Base64.decode(arrSP.get(i).getImage(), Base64.DEFAULT)).into(viewHolder.vAnh);
        viewHolder.vTenSP.setText(arrSP.get(i).getTenSP());
        viewHolder.vGiaCu.setText(arrSP.get(i).getGiaCu() + "");
        viewHolder.vGiaMoi.setText(arrSP.get(i).getGiaMoi() + "");
        viewHolder.vGiaCu.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return arrSP.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        ImageView vAnh;
        TextView vTenSP;
        TextView vGiaCu, vGiaMoi;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            this.cv = itemView.findViewById(R.id.cvSP);
            this.vAnh = itemView.findViewById(R.id.itemAnh);
            this.vTenSP = itemView.findViewById(R.id.itemTxtTenSP);
            this.vGiaCu = itemView.findViewById(R.id.itemGiaCu);
            this.vGiaMoi = itemView.findViewById(R.id.itemGiaMoi);
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

    public void filter(String s) {
        s = s.toLowerCase(Locale.getDefault());
        arrSP.clear();
        if (MainActivity.network == false) {
            arrPhu.clear();
            arrPhu.addAll(db.getAllSanPham());
        }
        if (s.length() == 0) {
            arrSP.addAll(arrPhu);
        } else {
            for (SanPhamEntity sp : arrPhu) {
                if (sp.getTenSP().toLowerCase(Locale.getDefault()).contains(s)) {
                    arrSP.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
