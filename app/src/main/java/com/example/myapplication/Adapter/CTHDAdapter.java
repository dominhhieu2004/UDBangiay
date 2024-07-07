package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dao.HoaDonDao;
import com.example.myapplication.Dao.SanPhamDao;
import com.example.myapplication.Model.ChiTietHoaDon;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CTHDAdapter extends RecyclerView.Adapter<CTHDAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ChiTietHoaDon> lstCTHD;

    public CTHDAdapter(Context context, ArrayList<ChiTietHoaDon> lstCTHD) {
        this.context = context;
        this.lstCTHD = lstCTHD;
    }

    @NonNull
    @Override
    public CTHDAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchi_tiet_don_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CTHDAdapter.ViewHolder holder, int position) {
        ChiTietHoaDon cthd = lstCTHD.get(position);

        SanPhamDao sanPhamDao = new SanPhamDao(context);
        SanPham sanPham = sanPhamDao.getID(String.valueOf(cthd.getMaSPCTHD()));
        holder.tvTenSP.setText("Ten san pham : " + sanPham.getTenSP());
        Picasso.get().load(sanPham.getImages()).into(holder.imagseCTHD);

        holder.tvSoLuong.setText("So luong:" + cthd.getSoLuong());


    }

    @Override
    public int getItemCount() {
        return lstCTHD.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagseCTHD;
        TextView tvTenSP, tvSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagseCTHD = itemView.findViewById(R.id.item_imgchiTietHD);
            tvTenSP = itemView.findViewById(R.id.item_tenSpChiTietHD);
            tvSoLuong = itemView.findViewById(R.id.item_soLuongChiTietHD);
        }
    }
}
