package com.example.myapplication.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.Activity.GioHangActivity;
import com.example.myapplication.Activity.Login_khActivity;
import com.example.myapplication.Adapter.SanPhamKHAdapter;
import com.example.myapplication.Dao.SanPhamDao;
import com.example.myapplication.IClick.IClickItemRCV;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.R;
import com.example.myapplication.Untils.untils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class HomeKH extends AppCompatActivity {

    BottomNavigationView bottm ;

    RecyclerView rcv_spKH;

    SanPhamDao sanPhamDao;

    ArrayList<SanPham> lstSanPham;
    SanPhamKHAdapter sanPhamKHAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_kh);

        bottm = findViewById(R.id.navigation);
        rcv_spKH = findViewById(R.id.rcvsp_kh);

        lstSanPham = new ArrayList<>();
        sanPhamDao = new SanPhamDao(this);

        bottm.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.navigation_out){
                    AlertDialog.Builder buider = new AlertDialog.Builder(HomeKH.this);
                    buider.setMessage("Bạn có muốn đăng xuất?");
                    buider.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(HomeKH.this, Login_khActivity.class));
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
                } else if (item.getItemId() == R.id.navigation_cart) {
                    startActivity(new Intent(HomeKH.this, GioHangActivity.class));
                }
                return true;
            }
        });

        // Đặt fragment mặc định được hiển thị khi activity bắt đầu
        bottm.setSelectedItemId(R.id.navigation_home);


        if (untils.mangGioHang == null){
            untils.mangGioHang = new ArrayList<>();
        }

        loadData();
    }

    public void loadData(){
        lstSanPham =(ArrayList<SanPham>) sanPhamDao.getAll();
        sanPhamKHAdapter = new SanPhamKHAdapter(getApplicationContext(), lstSanPham, new IClickItemRCV() {
            @Override
            public void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type) {

            }
        });

        rcv_spKH.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rcv_spKH.setAdapter(sanPhamKHAdapter);
    }
}