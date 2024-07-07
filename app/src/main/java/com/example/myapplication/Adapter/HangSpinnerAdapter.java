package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Model.Hang;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class HangSpinnerAdapter extends ArrayAdapter<Hang> {

    Context context;
    ArrayList<Hang> lstHang;

    TextView tv_maHang, tv_tenHang;

    public HangSpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList lstHang) {
        super(context, resource, lstHang);
        this.context = context;
        this.lstHang = lstHang;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_spinner, parent, false);

        final  Hang hang = lstHang.get(position);

        if(hang != null){
            tv_maHang = convertView.findViewById(R.id.tv_maLoai);
            tv_maHang.setText(String.valueOf(hang.getMaHang()));
            tv_tenHang = convertView.findViewById(R.id.tv_tenLoai);
            tv_tenHang.setText(hang.getTenHang());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_selected, parent, false);

        final  Hang hang = lstHang.get(position);


        if(hang != null){
            tv_maHang = convertView.findViewById(R.id.tv_maLoai);
            tv_maHang.setText(String.valueOf(hang.getMaHang()));
            tv_tenHang = convertView.findViewById(R.id.tv_tenLoai);
            tv_tenHang.setText(hang.getTenHang());
        }

        return convertView;
    }
}
