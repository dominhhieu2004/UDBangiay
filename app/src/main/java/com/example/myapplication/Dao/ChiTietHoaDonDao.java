package com.example.myapplication.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.ChiTietHoaDon;
import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDao {
    private DbHelper dbHelper;

    public ChiTietHoaDonDao (Context context){
        this.dbHelper = new DbHelper(context);
    }

     public long insertCTHD(ChiTietHoaDon obj){
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put("maHD", obj.getMaHoaDon());
         values.put("imagesCTHD", obj.getImgaesCTHD());
         values.put("maSPCTHD", obj.getMaSPCTHD());
         values.put("soLuongCTHD", obj.getSoLuong());

         return db.insert("CTHoaDon", null, values);
     }

    public List<ChiTietHoaDon> getAll() {
        String sql = "SELECT * FROM CTHoaDon";
        return getData(sql);
    }


    public List<ChiTietHoaDon> getAllByID(String id){
         SQLiteDatabase db = dbHelper.getReadableDatabase();
         List<ChiTietHoaDon> lstCTHD = new ArrayList<>();

        String sql = "SELECT * FROM CTHoaDon WHERE maHD =?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});

        while (cursor.moveToNext()){
            lstCTHD.add(new ChiTietHoaDon(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
            ));
        }
        return lstCTHD;
    }

     public List<ChiTietHoaDon> getData(String sql, String... Args){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ChiTietHoaDon> lstCTHD = new ArrayList<>();

         Cursor cursor = db.rawQuery(sql, Args);

         while (cursor.moveToNext()){
             lstCTHD.add(new ChiTietHoaDon(
                     cursor.getInt(0),
                     cursor.getInt(1),
                     cursor.getString(2),
                     cursor.getInt(3),
                     cursor.getInt(4)
             ));
         }
         return lstCTHD;
     }
}
