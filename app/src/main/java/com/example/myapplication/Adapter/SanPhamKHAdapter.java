package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.ChiTietSanPhamActivity;
import com.example.myapplication.Dao.HangDao;
import com.example.myapplication.IClick.IClickItemRCV;
import com.example.myapplication.Model.Hang;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamKHAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    Context context;
    ArrayList<SanPham> lstSP;
    IClickItemRCV iClick;

    ArrayList<Hang> lstHang;
    HangDao hangDao;

    public SanPhamKHAdapter(Context context, ArrayList<SanPham> lstSP, IClickItemRCV iClick) {
        this.context = context;
        this.lstSP = lstSP;
        this.iClick = iClick;
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp, parent, false);
        return new SanPhamAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {
        SanPham sp = lstSP.get(position);
        hangDao = new HangDao(context);

        holder.tv_maSP.setText("Mã san pham: " + sp.getMaSP());
        holder.tv_tenSP.setText("Ten san pham : " + sp.getTenSP());
        holder.tv_soLuong.setText("SoLuong: " + sp.getSoLuong());
        holder.tv_giaTien.setText("Gia tien : " + sp.getGiaTien());
        holder.tv_moTa.setText("Mo ta: " + sp.getMoTa());

        Hang hang = hangDao.getID(String.valueOf(sp.getMaHang()));
        holder.tv_tenHang.setText("Ten Hang: " + hang.getTenHang());

            Picasso.get().load(sp.getImages()).into(holder.imgaesSP);
       // Hoặc đặt thuộc tính View.GONE để làm ImageButton biến mất và giải phóng không gian trong layout
        holder.btn_delete.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ChiTietSanPhamActivity.class);
                i.putExtra("id", lstSP.get(holder.getAdapterPosition()).getMaSP());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstSP != null ? lstSP.size():0;
    }



}
