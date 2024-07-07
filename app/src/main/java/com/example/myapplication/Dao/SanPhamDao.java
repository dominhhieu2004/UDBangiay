package com.example.myapplication.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.SanPham;
import com.example.myapplication.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDao {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public SanPhamDao(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    public long insert(SanPham obj) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSP", obj.getTenSP());
        contentValues.put("maHang", obj.getMaHang());
        contentValues.put("moTa", obj.getMoTa());
        contentValues.put("giaTien", obj.getGiaTien());
        contentValues.put("soLuong", obj.getSoLuong());
        contentValues.put("images", obj.getImages());

        return db.insert("SanPham", null, contentValues);
    }

    public long update(SanPham obj) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("tenSP", obj.getTenSP());
        contentValues.put("maHang", obj.getMaHang());
        contentValues.put("moTa", obj.getMoTa());
        contentValues.put("giaTien", obj.getGiaTien());
        contentValues.put("soLuong", obj.getSoLuong());
        contentValues.put("images", obj.getImages());

        return db.update("SanPham", contentValues, "maSP = ?", new String[]{String.valueOf(obj.getMaSP())});
    }

    public int delete(String id) {
        return db.delete("SanPham", "maSP = ?", new String[]{String.valueOf(id)});
    }

    private List<SanPham> getData(String sql, String... selectionArgs) {
        List<SanPham> lstSP = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()) {
            lstSP.add(new SanPham(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Integer.parseInt(cursor.getString(2)),
                    cursor.getString(3),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    cursor.getString(6)

            ));
        }
        return lstSP;
    }

    public SanPham getID(String maSP) {
        String sql = "SELECT * FROM SanPham WHERE maSP = ?";
        List<SanPham> lstTT = getData(sql, maSP);
        return lstTT.get(0);
    }

    public List<SanPham> getAll() {
        String sql = "SELECT * FROM SanPham";
        return getData(sql);
    }


    public SanPham selectID(int id) {
        SanPham sanPham = new SanPham();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM SanPham WHERE maSP =?", new String[]{String.valueOf(id)});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    SanPham sp = new SanPham();
                    sp.setMaSP(cursor.getInt(0));
                    sp.setTenSP(cursor.getString(1));
                    sp.setMaHang(cursor.getInt(2));
                    sp.setMoTa(cursor.getString(3));
                    sp.setGiaTien(cursor.getInt(4));
                    sp.setSoLuong(cursor.getInt(5));
                    sp.setImages(cursor.getString(6));
                    sanPham = sp;
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {

        }

        return sanPham;
    }
}


