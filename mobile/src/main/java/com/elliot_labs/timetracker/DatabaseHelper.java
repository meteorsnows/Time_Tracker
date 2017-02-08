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

    private static final String COL_ID = "ID INTEGER PRIMARY KEY AUTOINCREMENT";

    private static final String TABLE_SAVED = "saved_times";
    private static final String COL_DATE = "initial_date INTEGER";
    private static final String COL_TOTAL = "total_time INTEGER";
    private static final String COL_CATEGORY = "category INTEGER";

    private static final String TABLE_CATEGORIES = "categories";
    private static final String COL_NAME = "name TEXT";
    private static final String COL_PARENT = "parent INTEGER";

    private static final String TABLE_WORKING = "in_progress";
    private static final String COL_START = "start_time INTEGER";
    private static final String COL_CURRENT_TOTAL = "current_total INTEGER";
    private static final String COL_WORKING_CATEGORY = "category TEXT";



    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CATEGORIES + " ("
                + COL_ID + ","
                + COL_NAME + ","
                + COL_PARENT + ")");
        db.execSQL("create table " + TABLE_SAVED + " ("
                + COL_ID + ","
                + COL_DATE + ","
                + COL_TOTAL + ","
                + COL_CATEGORY + ")");
        db.execSQL("create table " + TABLE_WORKING + " ("
                + COL_ID + ","
                + COL_START + ","
                + COL_CURRENT_TOTAL + ","
                + COL_WORKING_CATEGORY + ")");

        // Creates teh tables and columns in the DB for data storage
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // What needs to happen when a DB upgrade happens. Place code here if needed.
    }

    boolean addDataCategories(String name, Integer parent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NAME.split(" ", 0)[0], name);
        if (parent != null){
            if (parent > 0) {
                contentValues.put(COL_PARENT.split(" ", 0)[0], parent);
            } else {
                contentValues.put(COL_PARENT.split(" ", 0)[0], 0);
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
