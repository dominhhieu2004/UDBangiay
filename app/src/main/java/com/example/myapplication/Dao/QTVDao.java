package com.example.myapplication.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.Model.QTV;
import com.example.myapplication.database.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class QTVDao {

    private SQLiteDatabase db;

    public QTVDao(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

     public long insert (QTV obj){
         ContentValues contentValues = new ContentValues();
         contentValues.put("maQTV", obj.getMaQTV());
         contentValues.put("matKhau", obj.getMatKhau());

         return db.insert("QTV", null, contentValues);
     }

   public long update(QTV obj){
        ContentValues contentValues = new ContentValues();
       contentValues.put("maQTV", obj.getMaQTV());
       contentValues.put("matKhau", obj.getMatKhau());

       return db.update("QTV", contentValues, "maQTV=?", new String[]{obj.getMaQTV()});
   }


   private List<QTV> getData(String sql, String...selectionArgs){
        List<QTV> lst = new ArrayList<>();
       Cursor cursor = db.rawQuery(sql, selectionArgs);
       while (cursor.moveToNext()){
           lst.add(new QTV(cursor.getString(0),
                   cursor.getString(1) ));
       }
       return lst;
   }

   public QTV getID(String id){
        String sql = "SELECT * FROM QTV WHERE maQTV";
        List<QTV> lst = getData(sql, id);
        return lst.get(0);
   }

   public List<QTV> getAll(){
        String sql = "SELECT * FROM QTV";
        return getData(sql);
   }

   public long checkLogin (String user, String pass){
        Cursor cursor = db.rawQuery("SELECT * FROM QTV WHERE maQTV=? AND matKhau=?", new String[]{user, pass});
        if(cursor.getCount() > 0){
            return 1;
        } else {
            return -1;
        }
   }
}
