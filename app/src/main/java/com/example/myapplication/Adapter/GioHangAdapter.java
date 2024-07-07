package com.example.myapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.GioHangActivity;
import com.example.myapplication.Dao.HangDao;
import com.example.myapplication.Dao.SanPhamDao;
import com.example.myapplication.IClick.IClickItemDelete;
import com.example.myapplication.IClick.IClickItemRCV;
import com.example.myapplication.IClick.IImageClichListener;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.Hang;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.Untils.untils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private Context context;
    private List<GioHang> lstGioHang;


    IClickItemRCV clickItemRCV;

    IClickItemDelete clickItemDelete;

    public void setClickItemDelete(IClickItemDelete clickItemDelete) {
        this.clickItemDelete = clickItemDelete;
    }

    public GioHangAdapter(Context context, List<GioHang> lstGioHang, IClickItemRCV clickItemRCV) {
        this.context = context;
        this.lstGioHang = lstGioHang;
        this.clickItemRCV = clickItemRCV;
    }

    @NonNull
    @Override
    public GioHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.ViewHolder holder, int position) {
       GioHang gh = lstGioHang.get(position);

        SanPhamDao sanPhamDao = new SanPhamDao(context);
        HangDao hangDao = new HangDao(context);

        SanPham sanPham = sanPhamDao.getID(String.valueOf(gh.getMaSP()));
        Hang hang =  hangDao.getID(String.valueOf(gh.getMaHang()));

        holder.tvmaSP.setText("Mã sản phẩm: "+sanPham.getTenSP());
        holder.tvTenSP.setText("Tên sản phẩm: "+sanPham.getTenSP());
        holder.tvHangSP.setText("Hãng: "+hang.getTenHang());
        holder.tvGia.setText("Giá: " + sanPham.getGiaTien() + "đ" );
        holder.tvSoLuong.setText(gh.getSoluong() + " ");
        Picasso.get().load(sanPham.getImages()).into(holder.images);

        holder.chkGioHang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        //true
                        untils.mangGioHang.get(holder.getAdapterPosition()).setCheck(true);
                        if(!untils.mangMuaHang.contains(gh)){
                           untils.mangMuaHang.add(gh);
                            GioHangActivity.tinhTongTien();
                        }
                    } else {
                        untils.mangGioHang.get(holder.getAdapterPosition()).setCheck(false);
                        for (int i = 0; i< untils.mangMuaHang.size(); i++){
                            if(untils.mangMuaHang.get(i).getMaSP() == gh.getMaSP()){
                                untils.mangMuaHang.remove(i);
                                GioHangActivity.tinhTongTien();
                            }
                        }
                    }
            }
        });

        holder.chkGioHang.setChecked(gh.isCheck());

        holder.setListenner(new IImageClichListener() {
            @Override
            public void onImageClick(View view, int pos, int giaTri) {
                if (giaTri == 1){
                    if (lstGioHang.get(pos).getSoluong() >1){
                        int soLuongMoi = lstGioHang.get(pos).getSoluong() - 1;
                        lstGioHang.get(pos).setSoluong(soLuongMoi);
                        notifyDataSetChanged();
                    }
                } else if (giaTri == 2) {
                    if(lstGioHang.get(pos).getSoluong() < 100){
                        int soLuongMoi = lstGioHang.get(pos).getSoluong() + 1;
                        lstGioHang.get(pos).setSoluong(soLuongMoi);
                        notifyDataSetChanged();
                    }
                    
                }
                holder.tvSoLuong.setText(lstGioHang.get(pos).getSoluong() + " ");
                GioHangActivity.tinhTongTien();
            }
        });


    }

    @Override
    public int getItemCount() {
        return lstGioHang != null ? lstGioHang.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvmaSP, tvTenSP, tvHangSP, tvGia, tvSoLuong;
        ImageView btnDelete, images, imagesTru, imagesCong;

        IImageClichListener listenner;

        CheckBox chkGioHang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvmaSP = itemView.findViewById(R.id.maSP_gioHang);
            tvTenSP = itemView.findViewById(R.id.tensanpham_gioHang);
            tvHangSP = itemView.findViewById(R.id.tenHang_gioHang);
            tvGia = itemView.findViewById(R.id.giatien_gioHang);
            tvSoLuong= itemView.findViewById(R.id.soLuong_gioHang);
            images = itemView.findViewById(R.id.images_gioHang);

            chkGioHang = itemView.findViewById(R.id.item_gioHang_check);
            btnDelete = itemView.findViewById(R.id.btn_delete_gioHang);

            imagesTru = itemView.findViewById(R.id.item_gioHang_tru);
            imagesCong = itemView.findViewById(R.id.item_gioHang_cong);

            //event click
            imagesCong.setOnClickListener(this);
            imagesTru.setOnClickListener(this);
            btnDelete.setOnClickListener(this);

        }

        public void setListenner(IImageClichListener listenner) {
            this.listenner = listenner;
        }


        @Override
        public void onClick(View view) {
            if (view == imagesTru){
                listenner.onImageClick(view, getAdapterPosition(), 1);
                //1 tru
            } else if(view == imagesCong){
                listenner.onImageClick(view, getAdapterPosition(), 2);
                //2 cộng
            } else if (view == btnDelete) {
                // Xử lý sự kiện nhấp nút xóa
                // Gọi một phương thức để xử lý việc xóa mục, truyền vị trí adapter
                handleDeleteButtonClick(getAdapterPosition());
                GioHangActivity.tinhTongTien();
            }
        }
        private void handleDeleteButtonClick(int position) {
            // Đảm bảo vị trí là hợp lệ
            if (position != RecyclerView.NO_POSITION && clickItemDelete != null) {
                // Thông báo cho giao diện clickItemDelete về hành động xóa
                clickItemDelete.onItemClick(position);
            }
        }
    }
}
