package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.ChiTietHoaDonActivity;
import com.example.myapplication.Dao.KhachHangDao;
import com.example.myapplication.Dao.SanPhamDao;
import com.example.myapplication.IClick.IClickItemRCV;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.KhachHang;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;

import java.util.ArrayList;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {

    private ArrayList<HoaDon> lstHoaDon;
    private Context context;

    IClickItemRCV clickItemRCV;

    public HoaDonAdapter(ArrayList<HoaDon> lstHoaDon, Context context, IClickItemRCV clickItemRCV) {
        this.lstHoaDon = lstHoaDon;
        this.context = context;
        this.clickItemRCV = clickItemRCV;
    }

    @NonNull
    @Override
    public HoaDonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdon_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapter.ViewHolder holder, int position) {
        HoaDon hoaDon = lstHoaDon.get(position);

        holder.tvIDHoaDon.setText("Mã hóa đơn: " + hoaDon.getMaHoaDon());

        //khach hang
        KhachHangDao khachHangDao = new KhachHangDao(context);
        KhachHang khachHang = khachHangDao.getID(hoaDon.getMaKH());
        holder.tvIDKH.setText("Ten khach hang: " + khachHang.getTenKH());

        //san pham
        SanPhamDao sanPhamDao = new SanPhamDao(context);
        SanPham sanPham = sanPhamDao.getID(String.valueOf(hoaDon.getMaSP()));
        holder.tvIDSP.setText("Ten san pham: " + sanPham.getTenSP());

        holder.tvTongTien.setText("Tong tien: " + hoaDon.getTongTien());
        holder.tvidNgayDat.setText("Ngay dat: " + hoaDon.getNgayDat());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ChiTietHoaDonActivity.class);
                i.putExtra("idHD", lstHoaDon.get(holder.getAdapterPosition()).getMaHoaDon());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstHoaDon != null ? lstHoaDon.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvIDHoaDon, tvIDKH, tvIDSP, tvTongTien, tvidNgayDat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIDHoaDon = itemView.findViewById(R.id.idHoaDon);
            tvIDKH = itemView.findViewById(R.id.idKhachHang);
            tvIDSP = itemView.findViewById(R.id.idSanPham);
            tvTongTien = itemView.findViewById(R.id.tongTien);
            tvidNgayDat = itemView.findViewById(R.id.ngayDat);
        }
    }
}
