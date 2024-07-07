package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class LuaChon_Activity extends AppCompatActivity {

    Button btnAdmin, btnKH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lua_chon);

        btnAdmin = findViewById(R.id.admin);
        btnKH = findViewById(R.id.kh);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LuaChon_Activity.this, Login_adminActivity.class));
            }
        });

        btnKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LuaChon_Activity.this, Login_khActivity.class));
            }
        });
    }
}