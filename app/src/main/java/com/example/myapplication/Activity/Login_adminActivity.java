package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Dao.QTVDao;
import com.example.myapplication.Home.HomeAdmin;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputLayout;

public class Login_adminActivity extends AppCompatActivity {

    Button btnLogin, btnOut;
    EditText edtTK, edtMK;

    TextInputLayout w_username,w_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        edtTK = findViewById(R.id.txt_username);
        edtMK = findViewById(R.id.txt_password);
        w_username = findViewById(R.id.w_username);
        w_password = findViewById(R.id.w_password);
        btnOut = findViewById(R.id.btn_out);
        btnLogin = findViewById(R.id.btn_login);

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_adminActivity.this, LuaChon_Activity.class));
            }
        });

        logIn();
    }

    private void logIn() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String user = edtTK.getText().toString().trim();
               String pass = edtMK.getText().toString().trim();

               if(user.isEmpty() || pass.isEmpty()){
                   if(user.isEmpty()){
                       w_username.setError("Không được để trống trường này !");
                   } else {
                       w_username.setErrorEnabled(false);
                   }

                   if(pass.isEmpty()){
                       w_password.setError("Không được để trống trường này !");
                   } else {
                       w_password.setErrorEnabled(false);
                   }
               } else {
                   QTVDao qtvDao = new QTVDao(Login_adminActivity.this);
                   if(qtvDao.checkLogin(user, pass) > 0){
                       Toast.makeText(Login_adminActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(Login_adminActivity.this, HomeAdmin.class));
                   } else {
                       Toast.makeText(Login_adminActivity.this, "Tài khoản hoặc mật khẩu chưa chính xác !", Toast.LENGTH_SHORT).show();
                   }
               }
            }
        });
    }
}