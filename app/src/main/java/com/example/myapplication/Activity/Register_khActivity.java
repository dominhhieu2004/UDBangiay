package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Dao.KhachHangDao;
import com.example.myapplication.R;

public class Register_khActivity extends AppCompatActivity {

    Button btnOut_kh, btndk;
    EditText edtHoTen, edtDiaChi, edtSDT, edtTaiKhoan, edtMatKhau, edtConf;

    KhachHangDao khachHangDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_kh);

        btnOut_kh = findViewById(R.id.btnOut_dk);
        btnOut_kh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register_khActivity.this, Login_khActivity.class));
            }
        });

        khachHangDao = new KhachHangDao(this);

        initUI();

        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String user = edtTaiKhoan.getText().toString().trim();
              String pass = edtMatKhau.getText().toString().trim();
              String hoTen = edtHoTen.getText().toString().trim();
              String diaChi = edtDiaChi.getText().toString().trim();
              String sdt = edtSDT.getText().toString().trim();
              String conf = edtConf.getText().toString().trim();

              if(user.isEmpty() || pass.isEmpty() || hoTen.isEmpty() || diaChi.isEmpty() || sdt.isEmpty() || conf.isEmpty()){
                  Toast.makeText(Register_khActivity.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
              }else if(pass.length() < 8){
                  Toast.makeText(Register_khActivity.this, "Mật khẩu phải lớn hơn 8 ký tự", Toast.LENGTH_SHORT).show();
              } else {
                  if(pass.equals(conf)){
                      boolean ktTK = khachHangDao.checkTH(user);
                      if(ktTK){
                          Toast.makeText(Register_khActivity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                          return;
                      }

                      boolean kt = khachHangDao.dangKy(user, pass, hoTen, diaChi, sdt);
                      if(kt){
                          Toast.makeText(Register_khActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                      } else {
                          Toast.makeText(Register_khActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                      }
                      
                  } else {
                      Toast.makeText(Register_khActivity.this, "Trường mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                  }
              }
            }
        });

    }

    private void initUI(){
        btndk = findViewById(R.id.btnDk_dk);
        edtHoTen = findViewById(R.id.edHoTen_dk);
        edtDiaChi = findViewById(R.id.edDiaChi_dk);
        edtSDT = findViewById(R.id.edSDT_dk);
        edtTaiKhoan = findViewById(R.id.edTaiKhoan_dk);
        edtMatKhau = findViewById(R.id.edMatKhau_dk);
        edtConf = findViewById(R.id.edConf_dk);
    }


}