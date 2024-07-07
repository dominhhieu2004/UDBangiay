package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.GioHangAdapter;
import com.example.myapplication.Dao.ChiTietHoaDonDao;
import com.example.myapplication.Dao.HoaDonDao;
import com.example.myapplication.Home.HomeKH;
import com.example.myapplication.IClick.IClickItemDelete;
import com.example.myapplication.IClick.IClickItemRCV;
import com.example.myapplication.Model.ChiTietHoaDon;
import com.example.myapplication.Model.GioHang;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.R;
import com.example.myapplication.Untils.untils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class GioHangActivity extends AppCompatActivity implements IClickItemDelete {

    BottomNavigationView bottm;

    GioHangAdapter adapter;
    RecyclerView rcvGioHang;

    Button btnMuaHang;

    static TextView txtTongTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        toolBarCheck();
        Toolbar toolbar = findViewById(R.id.toolbar_ctsp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        loadData();

        //khi tích checkbox rồi nhưng thoát ra ngoài nó sẽ chuyển tổng tiền thành 0

        if (untils.mangMuaHang != null) {
            untils.mangMuaHang.clear();
        }
        tinhTongTien();
        themDonHang();
    }

    private void themDonHang() {
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muaHang();
                for (int i = 0; i < untils.mangMuaHang.size(); i++) {
                    //khi mua thi se xoa khoi man hinh
                    GioHang gioHang = untils.mangMuaHang.get(i);
                    if (untils.mangGioHang.contains(gioHang)) {
                        untils.mangGioHang.remove(gioHang);
                    }
                }
                untils.mangMuaHang.clear();
                startActivity(new Intent(GioHangActivity.this, HomeKH.class));
            }
        });
    }

    private void muaHang() {
        HoaDon hoaDon = new HoaDon();

        //lấy tổng tiền
        long tongTien = 0;
        for (int i = 0; i < untils.mangMuaHang.size(); i++) {
            tongTien = tongTien + (untils.mangMuaHang.get(i).getSoluong() * untils.mangMuaHang.get(i).getGia());
        }

        //lấy mã khách hàng
        SharedPreferences pref = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");

        //lấy mã sản phẩm
        int maSP = 0;
        for (int i = 0; i < untils.mangMuaHang.size(); i++) {
            maSP = untils.mangGioHang.get(i).getMaSP();
        }
        //lấy số luong
        int soLuong = 0;
        for (int i = 0; i < untils.mangMuaHang.size(); i++) {
            soLuong = untils.mangMuaHang.get(i).getSoluong();
        }

        //lấy ảnh
        String anh = null;
        for (int i = 0; i < untils.mangMuaHang.size(); i++) {
            anh = untils.mangGioHang.get(i).getImagesGH();
        }

        //lấy ngày
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = simpleDateFormat.format(date);

        HoaDonDao hoaDonDao = new HoaDonDao(this);
        //insert vao hoaDon
        hoaDon.setMaHoaDon(hoaDonDao.getAll().size() + 1);// phải có dòng này mới có thể lấy được mã Hóa Đơn để làm phần CTHD

        hoaDon.setMaKH(user);
        hoaDon.setMaSP(maSP);
        hoaDon.setTongTien((int) tongTien);
        hoaDon.setSoLuong(soLuong);
        hoaDon.setImgaesHD(anh);
        hoaDon.setNgayDat(dateString);

        ChiTietHoaDonDao chiTietHoaDonDao = new ChiTietHoaDonDao(this);
        //tao hoa don
        if (tongTien == 0) {
            Toast.makeText(this, "Hãy chọn sản phẩm bạn muốn mua!", Toast.LENGTH_SHORT).show();
        } else {
            if (hoaDonDao.insertIntoHD(hoaDon) > 0) {
                //tao hoa don chi tiet
                for (GioHang gioHang : untils.mangMuaHang) {
                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                    chiTietHoaDon.setMaHoaDon(hoaDon.getMaHoaDon());
                    chiTietHoaDon.setImgaesCTHD(gioHang.getImagesGH());
                    chiTietHoaDon.setMaSPCTHD(gioHang.getMaSP());
                    chiTietHoaDon.setSoLuong(gioHang.getSoluong());
                    //insert vao bang cthd
                    chiTietHoaDonDao.insertCTHD(chiTietHoaDon);
                }
                Toast.makeText(this, "Cám ơn bạn đã mua hàng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Mua thất bại!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static void tinhTongTien() {
        long tongTienSP = 0;
        for (int i = 0; i < untils.mangMuaHang.size(); i++) {
            tongTienSP = tongTienSP + (untils.mangMuaHang.get(i).getGia() * untils.mangMuaHang.get(i).getSoluong());
        }
        txtTongTien.setText(String.valueOf(tongTienSP) + " đ");
    }

    public void loadData() {
        adapter = new GioHangAdapter(getApplicationContext(), untils.mangGioHang, new IClickItemRCV() {
            @Override
            public void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type) {

            }
        });
        adapter.setClickItemDelete(this);  // this là đối tượng của Activity hoặc Fragment
        rcvGioHang.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rcvGioHang.setAdapter(adapter);
    }


    private void initView() {
        rcvGioHang = findViewById(R.id.rcv_gioHang);
        txtTongTien = findViewById(R.id.tvTongTienGioHang);
        btnMuaHang = findViewById(R.id.btnMuaHang);
    }


    public void toolBarCheck() {
        bottm = findViewById(R.id.navigation);
        bottm.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_out) {
                    AlertDialog.Builder buider = new AlertDialog.Builder(GioHangActivity.this);
                    buider.setMessage("Bạn có muốn đăng xuất?");
                    buider.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(GioHangActivity.this, Login_khActivity.class));
                        }
                    });

                    buider.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    buider.create();
                    buider.show();
                } else if (item.getItemId() == R.id.navigation_home) {
                    Intent i = new Intent(GioHangActivity.this, HomeKH.class);
                    startActivity(i);
                }

                return true;
            }
        });

        bottm.setSelectedItemId(R.id.navigation_cart);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            bottm.setSelectedItemId(R.id.navigation_home);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(int position) {
        // Đảm bảo vị trí là hợp lệ
        if (position >= 0 && position < untils.mangGioHang.size()) {
            // Xóa mục khỏi danh sách

            untils.mangGioHang.remove(position);
            // Thông báo cho adapter về việc loại bỏ mục
            adapter.notifyItemRemoved(position);
        }
    }

}