package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.R;

public class WelComeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel_come);

        Handler han = new Handler();
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                 startActivity(new Intent(WelComeActivity.this, LuaChon_Activity.class));
                finish();
            }
        }, 1500);
    }
}