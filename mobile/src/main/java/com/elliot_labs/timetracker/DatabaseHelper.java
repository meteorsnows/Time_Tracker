package com.elliot_labs.timetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;

/**
 * Creates database.
 */

class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Times.db";

    private static final String COL_GLOBAL_ID = "ID INTEGER PRIMARY KEY AUTOINCREMENT";

    private static final String TABLE_SAVED = "saved_times";
    private static final String COL_SAVED_DATE = "initial_date INTEGER";
    private static final String COL_SAVED_TOTAL = "total_time INTEGER";
    private static final String COL_SAVED_CATEGORY = "category INTEGER";

    private static final String TABLE_CATEGORIES = "categories";
    private static final String COL_CATEGORIES_NAME = "name TEXT";
    private static final String COL_CATEGORIES_PARENT = "parent INTEGER";

    private static final String TABLE_SETTINGS = "settings";
    private static final String COL_SETTINGS_NAME = "name TEXT";
    private static final String COL_SETTINGS_VALUE = "value TEXT";

    private static final String TABLE_TIMING = "currently_timing";
    private static final String COL_TIMING_CREATED = "date_created INTEGER";
    private static final String COL_TIMING_TIMING = "timing_from INTEGER";
    private static final String COL_TIMING_TOTAL = "current_total INTEGER";
    private static final String COL_TIMING_CATEGORY = "category INTEGER";



    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_SAVED + " ("
                + COL_GLOBAL_ID + ","
                + COL_SAVED_DATE + ","
                + COL_SAVED_TOTAL + ","
                + COL_SAVED_CATEGORY + ")");
        db.execSQL("create table " + TABLE_CATEGORIES + " ("
                + COL_GLOBAL_ID + ","
                + COL_CATEGORIES_NAME + ","
                + COL_CATEGORIES_PARENT + ")");
        db.execSQL("create table " + TABLE_SETTINGS + " ("
                + COL_GLOBAL_ID + ","
                + COL_SETTINGS_NAME + ","
                + COL_SETTINGS_VALUE + ")");
        db.execSQL("create table " + TABLE_TIMING + " ("
                + COL_GLOBAL_ID + ","
                + COL_TIMING_CREATED + ","
                + COL_TIMING_TIMING + ","
                + COL_TIMING_TOTAL + ","
                + COL_TIMING_CATEGORY + ")");

        // Creates the tables and columns in the DB for data storage
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // What needs to happen when a DB upgrade happens. Place code here if needed.
    }

    boolean addDataCategories(String name, Integer parent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_CATEGORIES_NAME.split(" ", 0)[0], name);
        if (parent != null){
            if (parent > 0) {
                contentValues.put(COL_CATEGORIES_PARENT.split(" ", 0)[0], parent);
            } else {
                contentValues.put(COL_CATEGORIES_PARENT.split(" ", 0)[0], 0);
            }
        }

        long errorCheck = db.insert(TABLE_CATEGORIES, null,contentValues);

        if (errorCheck == -1) {
            return false;
        } else {
            return true;
        }
    }

    SparseArray<String> getColumnData(String tableName, Integer columnID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor categories = db.rawQuery("select * from " + tableName, null);

        SparseArray<String> mappedData = new SparseArray<>();

        if (!categories.moveToFirst()) {
            //do something here if no data available
        } else {
            categories.moveToFirst();
            boolean categoryDataAvailable = true;

            while(categoryDataAvailable) {
                mappedData.put(categories.getInt(0), categories.getString(columnID));

                if (categories.moveToNext()){
                    categoryDataAvailable = true;
                } else {
                    categoryDataAvailable = false;
                }
            }
        }
        return mappedData;
    }

    int deleteByID(String table_name, Integer ID){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(table_name, "ID = ?", new String[] {ID.toString()});
    }

    boolean updateDataByID(String table_name, Integer ID, String col_name, String cell_data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(col_name, cell_data);

        db.update(table_name, contentValues, "ID = ?",new String[]{ID.toString()} );
        return true;

    }

}
