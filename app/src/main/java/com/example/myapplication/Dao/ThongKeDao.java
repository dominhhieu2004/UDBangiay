package com.example.myapplication.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.SanPham;
import com.example.myapplication.Model.Top;
import com.example.myapplication.database.DbHelper;

import java.util.ArrayList;

public class ThongKeDao {
    DbHelper dbHelper;
    Context context;

    public ThongKeDao(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Top> getTop() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "SELECT maSP ,Count(maSP) as soLuong FROM HoaDon GROUP BY maSP ORDER BY soLuong DESC LIMIT 10";
        ArrayList<Top> list = new ArrayList<>();
        SanPhamDao sanPhamDao = new SanPhamDao(context);
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Top top = new Top();
                SanPham sanPham = sanPhamDao.selectID(cursor.getInt(cursor.getColumnIndexOrThrow("maSP")));
                top.tenSP = sanPham.getTenSP();
                top.soLuong = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("soLuong")));
                list.add(top);
            } while (cursor.moveToNext());

        }
        return list;
    }


    public int DoanhThu(String tuNgay, String denNgay) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "SELECT SUM(tongTien) AS doanhThu FROM HoaDon WHERE ngayDat BETWEEN ? AND ?";
        String dk[] = {tuNgay, denNgay};
        int doanhThu = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, dk);
        if (cursor.moveToFirst()) {
            try {
                doanhThu = cursor.getInt(cursor.getColumnIndexOrThrow("doanhThu"));
            } catch (Exception e) {
                doanhThu = 0;
            }
        }
        cursor.close();
        return doanhThu;
    }
}
