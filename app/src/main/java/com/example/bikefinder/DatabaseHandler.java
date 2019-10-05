package com.example.bikefinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DatabaseHandler extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "location.db";
    public static final String TABLE_NAME = "location_table";
    public static final String COL_1 = "coordinates1";
    public static final String COL_2 = "coordinates2";
    public static final String COL_3 = "time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table" + TABLE_NAME + " (coordinates1 FLOAT,coordinates2 FLOAT, time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(float coordinates1, float coordinates2, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, coordinates1);
        contentValues.put(COL_2, coordinates2);
        contentValues.put(COL_3, time);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result ==-1)
            return false;
        else
            return true;



    }
}
