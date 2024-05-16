package com.example.taxi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SqliteDB_1501 extends SQLiteOpenHelper{
    private static final String DBName="SqliteDB_1501";
    private static final int  VERSION=1;
    private static final String TABLENAME="Taxi";
    private  static  String Id="_id";
    private static String SoXe="soxe";
    private static  String QuangDuong="quangduong";
    private static  String DonGia="dongia";
    private static  String PhanTram="phantram";
    private SQLiteDatabase myDB;

    public SqliteDB_1501(@Nullable Context context) {
        super(context, DBName,null, VERSION);
    }

    public static String getQuangDuong() {
        return QuangDuong;
    }

    public static String getDonGia() {
        return DonGia;
    }

    public static String getPhanTram() {
        return PhanTram;
    }

    public static String getSoXe() {
        return SoXe;
    }

    public static String getId() {
        return Id;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable = "CREATE TABLE " + TABLENAME +
                "( " + Id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SoXe + " TEXT NOT NULL, " +
                QuangDuong + " REAL NOT NULL, " +
                DonGia + " REAL NOT NULL, " +
                PhanTram + " INTEGER NOT NULL)";
        db.execSQL(queryTable);
        ArrayList<Taxi> list = new ArrayList<>();
        list.add(new Taxi(0, "29A2-283.31", 12.3,8800.0,5));
        list.add(new Taxi(1, "29B2-283.32", 13.3,9800.0,10));
        list.add(new Taxi(2, "29C2-283.33", 13.3,10800.0,15));
        list.add(new Taxi(3, "29D2-283.34", 15.3,11800.0,15));
        list.add(new Taxi(4, "29E2-283.35", 16.3,12800.0,10));
        list.add(new Taxi(5, "29F2-283.36", 17.3,13800.0,5));

        for (Taxi taxi : list) {
            ContentValues values = new ContentValues();
            values.clear();
            values.put(SoXe,taxi.getSoXe());
            values.put(QuangDuong,taxi.getQuangDuong());
            values.put(DonGia,taxi.getDonGia());
            values.put(PhanTram,taxi.getPhanTram());
            db.insert(TABLENAME, null, values);
        }



    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void openDb(){
        myDB= getWritableDatabase();
    }
    public void closeDb(){
        if(myDB !=null && myDB.isOpen()){
            myDB.close();
        }
    }
    public long Insert(String soxe, double quanduong,double dongia,int phantram){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(SoXe,soxe);
        values.put(QuangDuong,quanduong);
        values.put(DonGia,dongia);
        values.put(PhanTram,phantram);
        return db.insert(TABLENAME,null,values);
    }
    public Cursor DisplayAll(){
        SQLiteDatabase db = getReadableDatabase(); // Use getReadableDatabase() instead of myDB
        String query = "SELECT * FROM " + TABLENAME;
        return db.rawQuery(query, null);
    }
    public long Update(int id,String soxe, double quanduong,double dongia,int phantram){
        ContentValues values=new ContentValues();
        values.put(SoXe,soxe);
        values.put(QuangDuong,quanduong);
        values.put(DonGia,dongia);
        values.put(PhanTram,phantram);
        String where=Id+" = "+id;
        return  myDB.update(TABLENAME,values,where,null);
    }
    public long Delete(int id){
        String where=Id+ " = "+ id;
        return  myDB.delete(TABLENAME,where,null);
    }
}
