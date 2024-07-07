package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "QLBG";
    public static final int VER_SION = 8;


    public DbHelper(Context context) {
        super(context, DB_NAME, null, VER_SION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//admin
        String creatTableQTV = "CREATE TABLE QTV (" +
                "maQTV TEXT NOT NULL UNIQUE PRIMARY KEY," +
                "matKhau TEXT NOT NULL)";
        db.execSQL(creatTableQTV);
//khach hang
        String creatTableKhachHang = "CREATE TABLE KhachHang (" +
                "maKH TEXT NOT NULL UNIQUE PRIMARY KEY," +
                "hoTen TEXT NOT NULL," +
                "diaChi TEXT NOT NULL," +
                "sdt TEXT NOT NULL," +
                "matKhau TEXT NOT NULL)";
        db.execSQL(creatTableKhachHang);

        //hang
        String createTableHang = "CREATE TABLE Hang (" +
                "maHang INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenHang TEXT NOT NULL)";
        db.execSQL(createTableHang);

        //San pham
        String createTableSanPham = "CREATE TABLE SanPham (" +
                "maSP INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSP TEXT NOT NULL," +
                "maHang INTEGER REFERENCES Hang(maHang)," +
                "moTa TEXT NOT NULL," +
                "giaTien INTEGER NOT NULL," +
                "soLuong INTEGER NOT NULL," +
                "images TEXT NOT NULL)";
        db.execSQL(createTableSanPham);

//        String createTableSanPham = "CREATE TABLE SanPham (maSP TEXT PRIMARY KEY AUTOINCREMENT," +
//                "tenSP TEXT )";
//        db.execSQL(createTableSanPham);

        //Hoa don
        String createTableHoaDon = "CREATE TABLE HoaDon (" +
                "maHD INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maKH TEXT REFERENCES KhachHang(maKH)," +
                "maSP INTEGER REFERENCES SanPham (maSP)," +
                "tongTien INTEGER NOT NULL," +
                "soLuong INTEGER NOT NULL," +
                "imagaesHD TEXT NOT NULL," +
                "ngayDat TEXT NOT NULL)";
        db.execSQL(createTableHoaDon);

        //chi tiet hoa don
        String createTableCTHoaDon = "CREATE TABLE CTHoaDon (" +
                "maCTHD INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maHD INTEGER REFERENCES HoaDon (maHD)," +
                "imagesCTHD TEXT NOT NULL," +
                "maSPCTHD INTEGER NOT NULL," +
                "soLuongCTHD INTEGER NOT NULL)";
        db.execSQL(createTableCTHoaDon);

        String dataHang = "INSERT INTO Hang VALUES" +
                "(1, 'Nike')," +
                "(2, 'Converse')," +
                "(3, 'Adidas')";
        db.execSQL(dataHang);

        String dataQTV = "INSERT INTO QTV VALUES ('admin', '123456')";
        db.execSQL(dataQTV);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i != i1){
            db.execSQL("DROP TABLE IF EXISTS QTV");
            db.execSQL("DROP TABLE IF EXISTS KhachHang");
            db.execSQL("DROP TABLE IF EXISTS Hang");
            db.execSQL("DROP TABLE IF EXISTS SanPham");
            db.execSQL("DROP TABLE IF EXISTS HoaDon");
            db.execSQL("DROP TABLE IF EXISTS CTHoaDon");
            onCreate(db);
        }

    }
}
