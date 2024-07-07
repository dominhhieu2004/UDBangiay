package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.Adapter.CTHDAdapter;
import com.example.myapplication.Dao.ChiTietHoaDonDao;
import com.example.myapplication.Model.ChiTietHoaDon;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ChiTietHoaDonActivity extends AppCompatActivity {

    int maHD;
    ArrayList<ChiTietHoaDon> lstCTHD;
    ChiTietHoaDonDao cthdDao;

    CTHDAdapter adapter;

    RecyclerView rcvCTHD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_cthd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rcvCTHD = findViewById(R.id.rcv_ctHoaDon);
        cthdDao = new ChiTietHoaDonDao(this);
        lstCTHD = new ArrayList<>();

        Intent in = getIntent();
        maHD = in.getIntExtra("idHD", 0);
        lstCTHD = (ArrayList<ChiTietHoaDon>) cthdDao.getAllByID(String.valueOf(maHD));

        adapter = new CTHDAdapter(this, lstCTHD);
        rcvCTHD.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rcvCTHD.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}