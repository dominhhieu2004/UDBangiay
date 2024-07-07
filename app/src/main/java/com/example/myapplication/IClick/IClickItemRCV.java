package com.example.myapplication.IClick;

import androidx.recyclerview.widget.RecyclerView;

public interface IClickItemRCV {
    void iclickItem(RecyclerView.ViewHolder viewHolder, int position, int type);
}
