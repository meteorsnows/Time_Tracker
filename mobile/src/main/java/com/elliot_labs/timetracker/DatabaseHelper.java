package com.elliot_labs.timetracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Creates database.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Times.db";

    private static final String COL_ID = "ID INTEGER PRIMARY KEY AUTOINCREMENT";

    private static final String TABLE_SAVED = "saved_times";
    private static final String COL_DATE = "date INTEGER";
    private static final String COL_START_TIME = "start_time INTEGER";
    private static final String COL_STOP_TiIME = "stop_time INTEGER";
    private static final String COL_CATEGORY = "category TEXT";

    private static final String TABLE_CATEGORIES = "categories";
    private static final String COL_NAME = "name TEXT";
    private static final String COL_PARENT = "parent INTEGER";

    private static final String TABLE_WORKING = "in_progress";
    private static final String COL_START = "start_time INTEGER";
    private static final String COL_WORKING_CATEGORY = "category TEXT";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CATEGORIES + " (" + COL_ID + "," + COL_NAME +
                "," + COL_PARENT + ")");
        db.execSQL("create table " + TABLE_SAVED + " (" + COL_ID + "," + COL_DATE + ","
                + COL_START_TIME + "," + COL_STOP_TiIME + "," + COL_CATEGORY + ")");
        db.execSQL("create table " + TABLE_WORKING + " (" + COL_ID + "," + COL_START + ","
                + COL_WORKING_CATEGORY + ")");

        // Creates teh tables and collums in the DB for data storage
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // What needs to happen when a DB upgrade happens. Place code here if needed.
    }
}