package com.coolweather.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.coolweather.android.model.CountyModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final  int DATABASE_VERSION=1;
    private static  final  String DATABASE_NAME="favorite.db";
    private static  final String TABLE_NAME="county";
    private static  final String ID="id";
//    private String weatherId;
//    private String countyName;?
    private static final String COUNTY_NAME="countyName";
    private static final String WEATHER_ID="weatherId";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COUNTY_NAME + " TEXT, "
                + WEATHER_ID + " TEXT)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertCountyModel(String name, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COUNTY_NAME, name);
        contentValues.put(WEATHER_ID, gender);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return result != -1;
    }

    public List<CountyModel> getAll() {
        List<CountyModel> peopleList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String name = cursor.getString(cursor.getColumnIndex(COUNTY_NAME));
                String wid = cursor.getString(cursor.getColumnIndex(WEATHER_ID));
                CountyModel CountyModel = new CountyModel(id, wid, name);
                peopleList.add(CountyModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return peopleList;
    }

    public boolean deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result != 0;
    }

    public boolean deleteByName(String name) {
        Log.e("delete","delete:item-->"+name)
;        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COUNTY_NAME + " = ?", new String[]{name});
        db.close();
        return result != 0;
    }

    public boolean deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, null, null);
        db.close();
        return result != 0;
    }
}
