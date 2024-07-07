package com.example.myapplication.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.Hang;
import com.example.myapplication.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class HangDao {
    private DbHelper dbHelper;

    private SQLiteDatabase db;

    public HangDao(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(Hang obj){
        ContentValues values = new ContentValues();

        values.put("tenHang", obj.getTenHang());

        return db.insert("Hang", null, values);
    }

    public long update(Hang obj){
        ContentValues values = new ContentValues();
        values.put("maHang", obj.getMaHang());
        values.put("tenHang", obj.getTenHang());

        return db.update("Hang", values, "maHang =?", new String[]{String.valueOf(obj.getMaHang())});
    }

    public int delete(String id) {
        return db.delete("Hang","maHang = ?",new String[]{String.valueOf(id)});
    }

    private List<Hang> getData(String sql, String ...selectionArgs){
        List<Hang> lstHang = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            lstHang.add(new Hang(
                    Integer.parseInt(c.getString(0)),
                    c.getString(1)

            ));
        }
        return lstHang;
    }


    public Hang getID(String maHang){
       String sql = "SELECT * FROM Hang WHERE maHang=?";
       List<Hang> lstH =getData(sql, maHang);
       return lstH.get(0);
    }

    public List<Hang> getAll(){
        String sql = "SELECT * FROM Hang";
        return getData(sql);
    }
}
