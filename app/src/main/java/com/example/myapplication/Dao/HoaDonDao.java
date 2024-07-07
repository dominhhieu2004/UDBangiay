package com.example.myapplication.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.HoaDon;
import com.example.myapplication.Model.SanPham;
import com.example.myapplication.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class HoaDonDao {

    private DbHelper dbHelper;

    public HoaDonDao(Context context){
        this.dbHelper = new DbHelper(context);
    }

     public long insertIntoHD(HoaDon obj){
         SQLiteDatabase db = dbHelper.getWritableDatabase();
         ContentValues  values = new ContentValues();
         values.put("maKH", obj.getMaKH());
         values.put("maSP", obj.getMaSP());
         values.put("tongTien", obj.getTongTien());
         values.put("soLuong", obj.getSoLuong());
         values.put("imagaesHD", obj.getImgaesHD());
         values.put("ngayDat", obj.getNgayDat());

         return db.insert("HoaDon", null, values);
     }

     public List<HoaDon> getAll(){
         String sql = "SELECT * FROM HoaDon";
         return getData(sql);
     }

    public HoaDon getID(String maHD) {
        String sql = "SELECT * FROM HoaDon WHERE maHD = ?";
        List<HoaDon> lstHD = getData(sql, maHD);
        return lstHD.get(0);
    }
     public List<HoaDon> getData(String sql, String... Args){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<HoaDon> lstHoaDon = new ArrayList<>();
//int maHoaDon, String maKH, int maSP, int tongTien, int soLuong, String imgaesHD, String ngayDat
         Cursor cursor = db.rawQuery(sql, Args);
         while (cursor.moveToNext()){
             lstHoaDon.add(new HoaDon(
                     cursor.getInt(0),
                     cursor.getString(1),
                     cursor.getInt(2),
                     cursor.getInt(3),
                     cursor.getInt(4),
                     cursor.getString(5),
                     cursor.getString(6)
             ));
         }
         if (cursor != null){
             cursor.close();
         }
         if (db != null){
             db.close();
         }
         return lstHoaDon;
     }
}
