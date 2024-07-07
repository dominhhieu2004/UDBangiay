package com.example.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dao.HangDao;
import com.example.myapplication.IClick.IClickItemRCV;
import com.example.myapplication.Model.Hang;
import com.example.myapplication.R;

import java.util.ArrayList;

public class HangAdapter extends RecyclerView.Adapter<HangAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Hang> lstHang;

    private HangDao hangDao;
    IClickItemRCV clickItemRCV;

    public HangAdapter(Context context, ArrayList<Hang> lstHang, IClickItemRCV iClick) {
        this.context = context;
        this.lstHang = lstHang;
        this.clickItemRCV = iClick;
    }

    @NonNull
    @Override
    public HangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HangAdapter.ViewHolder holder, int position) {
        Hang hang = lstHang.get(position);
       holder.tvMa.setText("Mã hãng:" + hang.getMaHang());
       holder.tvTen.setText("Tên hãng:" + hang.getTenHang());

       holder.img_delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               clickItemRCV.iclickItem(holder, hang.getMaHang(), 1);
           }
       });

       holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               clickItemRCV.iclickItem(holder, holder.getAdapterPosition(), 0);
               return false;
           }
       });

    }

    @Override
    public int getItemCount() {
        return lstHang != null ? lstHang.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMa, tvTen;
        ImageButton img_delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa = itemView.findViewById(R.id.hang_maHang);
            tvTen = itemView.findViewById(R.id.hang_tenHang);
            img_delete = itemView.findViewById(R.id.btn_delete_hangAdmin);
        }
    }
}
