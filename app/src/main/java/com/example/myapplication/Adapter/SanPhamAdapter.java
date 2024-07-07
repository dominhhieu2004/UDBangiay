package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dao.HangDao;
import com.example.myapplication.IClick.IClickItemRCV;
import com.example.myapplication.Model.Hang;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    Context context;
    ArrayList<SanPham> lstSP;
    IClickItemRCV iClick;

    ArrayList<Hang> lstHang;
    HangDao hangDao;


    public SanPhamAdapter(Context context, ArrayList<SanPham> lstSP, IClickItemRCV iClick) {
        this.context = context;
        this.lstSP = lstSP;
        this.iClick = iClick;
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp, parent, false);
        return new ViewHolder(view);
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

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClick.iclickItem(holder, sp.getMaSP(), 1);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                iClick.iclickItem(holder, holder.getAdapterPosition(), 0);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstSP != null ? lstSP.size():0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_maSP, tv_tenSP, tv_soLuong, tv_giaTien, tv_moTa, tv_tenHang;

        ImageView imgaesSP;

        ImageButton btn_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgaesSP = itemView.findViewById(R.id.images_sp);
            tv_maSP = itemView.findViewById(R.id.sp_maSP);
            tv_tenSP = itemView.findViewById(R.id.sp_tenSP);
            tv_tenHang = itemView.findViewById(R.id.sp_hangSP);
            tv_giaTien = itemView.findViewById(R.id.sp_giatien);
            tv_moTa = itemView.findViewById(R.id.sp_moTa);
            tv_soLuong = itemView.findViewById(R.id.sp_soLuong);

            btn_delete = itemView.findViewById(R.id.btn_delete_spAdmin);
        }
    }
}
