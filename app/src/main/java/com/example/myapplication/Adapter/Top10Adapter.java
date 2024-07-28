package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.Model.Top;
import com.example.myapplication.R;

import java.util.ArrayList;

public class Top10Adapter extends BaseAdapter {

    Context context;
    ArrayList<Top> topList;

    public Top10Adapter(Context context, ArrayList<Top> topList) {
        this.context = context;
        this.topList = topList;
    }
    @Override
    public int getCount() {
        return topList.size();
    }

    @Override
    public Object getItem(int i) {
        return topList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class Top10ViewHolder {
        TextView txt_tenSP, txt_soLuong;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Top10ViewHolder top10ViewHolder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_top10, viewGroup, false);
            top10ViewHolder = new Top10ViewHolder();
            top10ViewHolder.txt_tenSP = view.findViewById(R.id.txt_tenSP_Top10);
            top10ViewHolder.txt_soLuong = view.findViewById(R.id.txt_soLuong_Top10);
            view.setTag(top10ViewHolder);
        } else {
            top10ViewHolder =(Top10ViewHolder) view.getTag();
        }
        Top top = topList.get(i);

        if (top != null) {
            top10ViewHolder.txt_tenSP.setText("Tên sản phẩm :" + topList.get(i).tenSP);
            top10ViewHolder.txt_soLuong.setText("Số lượng :" + topList.get(i).soLuong);
        }
        return view;
    }
}
