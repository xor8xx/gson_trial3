package com.example.niloychakma.gson_trial3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Drugs.db";
    public static final String TABLE_NAME = "save_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "brand_name";
    public static final String COL_3 = "generic_name";
    public static final String COL_4 = "indicationsInfo";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, brand_name TEXT, generic_name TEXT, indicationsInfo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String brand_name, String generic_name, String indicationsInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, brand_name);
        contentValues.put(COL_3, generic_name);
        contentValues.put(COL_4, indicationsInfo);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Integer deleteData (String brand, String generic) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "brand_name" + " = ? AND " + "generic_name" + " = ? " , new String[] {brand, generic});
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  " + TABLE_NAME,null);
        return res;
    }

}
