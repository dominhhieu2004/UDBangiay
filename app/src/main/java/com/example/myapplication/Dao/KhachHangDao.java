package com.example.myapplication.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.KhachHang;
import com.example.myapplication.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDao {
    SQLiteDatabase db;
    DbHelper dbHelper;
    public KhachHangDao(Context context){
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public boolean dangNhap (String user, String pass){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM KhachHang WHERE maKH =? AND matKhau =?", new String[]{user, pass});
        return cursor.getCount() > 0;
    }

    public boolean dangKy (String username, String pass, String hoTen, String diaChi, String sdt){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maKH", username);
        contentValues.put("matKhau", pass);
        contentValues.put("hoTen", hoTen);
        contentValues.put("diaChi", diaChi);
        contentValues.put("sdt", sdt);

        long raw  = db.insert("KhachHang", null, contentValues);
        return (raw > 0);
    }

    public boolean checkTH(String username){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c= db.rawQuery("SELECT * FROM KhachHang WHERE maKH=?", new String[]{username});
        return c.getCount()>0;
    }

    public long update(KhachHang obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("maKH", obj.getMaKH());
        contentValues.put("hoTen", obj.getTenKH());
        contentValues.put("diaChi", obj.getDiaChi());
        contentValues.put("sdt", obj.getSdt());
//        contentValues.put("matKhau", obj.getMatKhau());

        long raw = db.update("KhachHang", contentValues, "maKH =?", new String[]{obj.getMaKH()});
        return raw;
    }

    public long updateMK(KhachHang obj){
        ContentValues contentValues = new ContentValues();
        contentValues.put("matKhau", obj.getMatKhau());

        long raw = db.update("KhachHang", contentValues, "maKH =?", new String[]{obj.getMaKH()});
        return raw;
    }

    private List<KhachHang> getData (String sql, String...selectionArgs){
        List<KhachHang> lstKh = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            lstKh.add(new KhachHang(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));
        }
        return lstKh;
    }

    public KhachHang getID(String id){
        String sql = "SELECT * FROM KhachHang WHERE maKH=?";
        List<KhachHang> lst = getData(sql, id);
        return lst.get(0);
    }

    public List<KhachHang> getAll (){
        String sql = "SELECT * FROM KhachHang";
        return getData(sql);
    }
}
