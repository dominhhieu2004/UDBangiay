package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Dao.KhachHangDao;
import com.example.myapplication.Home.HomeKH;
import com.example.myapplication.R;

public class Login_khActivity extends AppCompatActivity {

    Button btnDn, btnDk, btnOut;

    EditText edTK, edMK;

    CheckBox chkKH;
    KhachHangDao khachHangDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_kh);

        edTK = findViewById(R.id.txt_username_kh);
        edMK = findViewById(R.id.txt_password_kh);
        chkKH = findViewById(R.id.chk_rememberAccount_kh);
        btnDk = findViewById(R.id.btn_dk_kh);
        btnDn = findViewById(R.id.btn_login_kh);
        btnOut = findViewById(R.id.btn_out_kh);

        khachHangDao = new KhachHangDao(this);

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user =pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        Boolean rem = pref.getBoolean("REMEMBER", false);

        edTK.setText(user);
        edMK.setText(pass);
        chkKH.setChecked(rem);

        btnDn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String user = edTK.getText().toString().trim();
              String pass = edMK.getText().toString().trim();
              boolean kt = khachHangDao.dangNhap(user, pass);

              if(kt){
                  Toast.makeText(Login_khActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                  rememberLogin(user, pass, chkKH.isChecked());
                  startActivity(new Intent(Login_khActivity.this, HomeKH.class));
              } else {
                  Toast.makeText(Login_khActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
              }
            }
        });

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_khActivity.this, LuaChon_Activity.class));
            }
        });

        btnDk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_khActivity.this, Register_khActivity.class));
            }
        });
    }

    public void rememberLogin(String u, String p, boolean status){
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if(!status){
            // xoa trang thai luu truoc do
            edit.clear();
        } else {
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        //lưu lại
        edit.commit();
    }
}