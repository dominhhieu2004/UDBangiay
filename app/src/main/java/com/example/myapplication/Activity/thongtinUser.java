package com.example.myapplication.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Dao.KhachHangDao;
import com.example.myapplication.Home.HomeKH;
import com.example.myapplication.Model.KhachHang;
import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class thongtinUser extends AppCompatActivity {
    BottomNavigationView bottm;

    KhachHangDao khachHangDao;
    ArrayList<KhachHang> listKhachHang;
    TextInputEditText edName, edPhone, edDiaChi, edMKcu, edMKmoi, edMKconf;
    KhachHang kh;
    Button btnCapNhat, btnDMK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thongtin_user);
        toolBarCheck();
        Toolbar toolbar = findViewById(R.id.toolbar_ctsp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        khachHangDao = new KhachHangDao(this);
        listKhachHang = new ArrayList<>();


        init();
        hienThi();
    }


    public void hienThi() {
        SharedPreferences pref = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");



        kh = khachHangDao.getID(user);
        KhachHang khachHang = new KhachHang();
        edName.setText(user);
        edPhone.setText(kh.getSdt());
        edDiaChi.setText(kh.getDiaChi());

        btnDMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capNhatMK();
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             edName.setText(kh.getTenKH());

            khachHang.setTenKH(edName.getText().toString());
            khachHang.setSdt(edPhone.getText().toString());
            khachHang.setDiaChi(edDiaChi.getText().toString());
             khachHang.setMaKH(edName.getText().toString());
             if(khachHangDao.update(khachHang) >0){
                 Toast.makeText(thongtinUser.this, "Chỉnh sửa thông tin thành công !", Toast.LENGTH_SHORT).show();
             } else {
                 Toast.makeText(thongtinUser.this, "Chỉnh sửa thông tin thất bại !", Toast.LENGTH_SHORT).show();
             }
            }
        });
    }

    public void capNhatMK() {
        AlertDialog.Builder builder = new AlertDialog.Builder(thongtinUser.this);
        LayoutInflater inflater = ((Activity) thongtinUser.this).getLayoutInflater();
        View v = inflater.inflate(R.layout.doimatkhau, null);
        builder.setView(v);
        Dialog dialog = builder.create();
        dialog.show();

        edMKcu = v.findViewById(R.id.edMatkhau_cu);
        edMKmoi = v.findViewById(R.id.edMatkhau_moi);
        edMKconf = v.findViewById(R.id.edMatkhau_conf);

        Button btndmk = v.findViewById(R.id.btn_doi_mk);
        btndmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String MKcu = edMKcu.getText().toString();
                String MKmoi = edMKmoi.getText().toString();
                String MKconf = edMKconf.getText().toString();
                if(validate(MKcu, MKmoi, MKconf)){
                    SharedPreferences pref = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                    String user = pref.getString("USERNAME", "");
                    KhachHang kh = khachHangDao.getID(user);
                    kh.setMatKhau(MKmoi);
                    if(khachHangDao.updateMK(kh) >0){
                        Toast.makeText(thongtinUser.this, "Đổi mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("PASSWORD", MKmoi);
                        editor.apply();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(thongtinUser.this, "Đổi mật khẩu thất bại !", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private boolean validate(String passWordOld, String passWordNew, String rePassWord) {
        if (passWordOld.isEmpty() || passWordNew.isEmpty() || rePassWord.isEmpty()) {
            Toast.makeText(thongtinUser.this, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        }  else {
            SharedPreferences pref = getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String pass_Old = pref.getString("PASSWORD", "");
            if (!passWordOld.equals(pass_Old)) {
                Toast.makeText(thongtinUser.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!passWordNew.equals(rePassWord)) {
                Toast.makeText(thongtinUser.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void toolBarCheck() {
        bottm = findViewById(R.id.navigation);
        bottm.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_out) {
                    AlertDialog.Builder buider = new AlertDialog.Builder(thongtinUser.this);
                    buider.setMessage("Bạn có muốn đăng xuất?");
                    buider.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(thongtinUser.this, Login_khActivity.class));
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
                    Intent i = new Intent(thongtinUser.this, HomeKH.class);
                    startActivity(i);
                } else if (item.getItemId() == R.id.navigation_cart) {
                    Intent i = new Intent(thongtinUser.this, GioHangActivity.class);
                    startActivity(i);
                }

                return true;
            }
        });

        bottm.setSelectedItemId(R.id.navigation_person);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            bottm.setSelectedItemId(R.id.navigation_home);
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {
         edName = findViewById(R.id.edNameTTKH);
         edPhone = findViewById(R.id.edSDTTTKH);
         edDiaChi = findViewById(R.id.edDiaChiTTKH);
         btnDMK = findViewById(R.id.btnDMKTTKH);
         btnCapNhat = findViewById(R.id.btnCapNhatTTKH);
    }
}