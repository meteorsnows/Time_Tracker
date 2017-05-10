package com.elliot_labs.timetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.SparseArray;

/**
 * Creates database.
 */

class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Times.db";

    private static final String COL_GLOBAL_ID = "ID INTEGER PRIMARY KEY AUTOINCREMENT";

    private static final String TABLE_SAVED = "saved_times";
    private static final String COL_SAVED_DATE = "date_created INTEGER";
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

    // Creates the initial database and its structure if it does not exist.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Time Tracker", "Database Create");
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
        Log.d("Time Tracker", "Database Upgrade");
        // What needs to happen when a DB upgrade happens. Place code here if needed.
    }


    // Each of the below data manipulation methods will return a boolean after execution,
    // True = the job was successful where as False indicates a failure.
    // For the data query methods, they will return an array with the requested data.


    // Adds data in the form of a string to the specified table (adds a new row)
    boolean addStringDataRow(String tableName, String columnName, String stringData) {
        Log.d("Time Tracker", "Database Add");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(columnName, stringData);

        long errorCheck = db.insert(tableName, null, contentValues);
        db.close();

        return (errorCheck != -1);
    }


    // Adds data in the form of a integer to the specified table (adds a new row)
    boolean addIntegerDataRow(String tableName, String columnName, Integer integerData) {
        Log.d("Time Tracker", "Database Add");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(columnName, integerData);

        long errorCheck = db.insert(tableName, null, contentValues);
        db.close();

        return (errorCheck != -1);
    }


    // Adds data in the form of a long type to the specified table (adds a new row)
    boolean addLongDataRow(String tableName, String columnName, Long longData) {
        Log.d("Time Tracker", "Database Add");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(columnName, longData);

        long errorCheck = db.insert(tableName, null, contentValues);
        db.close();

        return (errorCheck != -1);
    }


    // Returns the string data of the specified column from the given table in array format.
    SparseArray<String> getColumnStringData(String tableName, String columnName) {
        Log.d("Time Tracker", "Database read");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbCursor = db.rawQuery("select * from " + tableName, null);

        SparseArray<String> columnData = new SparseArray<>();
        int columnID = dbCursor.getColumnIndex(columnName);

        if (dbCursor.moveToFirst()) {
            boolean dataAvailable = true;

            while (dataAvailable) {
                columnData.put(dbCursor.getInt(0), dbCursor.getString(columnID));

                if (!dbCursor.moveToNext()) {
                    dataAvailable = false;
                }
            }
        }
        db.close();
        return columnData;
    }


    // Returns the integer data of the specified column from the given table in array format.
    SparseArray<Integer> getColumnIntegerData(String tableName, String columnName) {
        Log.d("Time Tracker", "Database read");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbCursor = db.rawQuery("select * from " + tableName, null);

        SparseArray<Integer> columnData = new SparseArray<>();
        int columnID = dbCursor.getColumnIndex(columnName);

        if (dbCursor.moveToFirst()) {
            boolean dataAvailable = true;

            while (dataAvailable) {
                columnData.put(dbCursor.getInt(0), dbCursor.getInt(columnID));

                if (!dbCursor.moveToNext()) {
                    dataAvailable = false;
                }
            }
        }
        db.close();
        return columnData;
    }


    // Returns the long data of the specified column from the given table in array format.
    SparseArray<Long> getColumnLongData(String tableName, String columnName) {
        Log.d("Time Tracker", "Database read");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor dbCursor = db.rawQuery("select * from " + tableName, null);

        SparseArray<Long> columnData = new SparseArray<>();
        int columnID = dbCursor.getColumnIndex(columnName);

        if (dbCursor.moveToFirst()) {
            boolean dataAvailable = true;

            while (dataAvailable) {
                columnData.put(dbCursor.getInt(0), dbCursor.getLong(columnID));

                if (!dbCursor.moveToNext()) {
                    dataAvailable = false;
                }
            }
        }
        db.close();
        return columnData;
    }


    // Updates the cell data in the specified table that matches the provided row ID and column name.
    boolean updateStringDataByID(String tableName, Integer ID, String columnName, String cellData) {
        Log.d("Time Tracker", "Database update");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(columnName, cellData);

        int result = db.update(tableName, contentValues, "ID = ?", new String[]{ID.toString()});
        db.close();

        return (result == 1);
    }


    // Updates the cell data in the specified table that matches the provided row ID and column name.
    boolean updateIntegerDataByID(String tableName, Integer ID, String columnName, Integer cellData) {
        Log.d("Time Tracker", "Database Update");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(columnName, cellData);

        int result = db.update(tableName, contentValues, "ID = ?", new String[]{ID.toString()});
        db.close();

        return (result == 1);
    }


    // Deletes the specified row in the selected table by the ID of the row.
    boolean deleteRowByID(String table_name, Integer ID) {
        Log.d("Time Tracker", "Database delete");
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(table_name, "ID = ?", new String[]{ID.toString()});
        db.close();

        return (result == 1);
    }
}