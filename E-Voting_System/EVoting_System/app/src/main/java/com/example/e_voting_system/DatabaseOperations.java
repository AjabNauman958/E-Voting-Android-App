package com.example.e_voting_system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseOperations(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertData(String tableName, ContentValues values) {
        return db.insert(tableName, null, values);
    }

    public boolean IsRecordExist(String tableName, String selection, String[] selectionArgs) {
        Cursor cursor = db.query(tableName, null, selection, selectionArgs, null, null, null);
        boolean recordExists = cursor.getCount() > 0;
        cursor.close();
        return recordExists;
    }

    public Cursor getData(String tableName, String[] columns, String selection, String[] selectionArgs) {

        return db.query(tableName, columns, selection, selectionArgs, null, null, null);
    }

    public int updateData(String tableName, ContentValues values, String selection, String[] selectionArgs) {
        return db.update(tableName, values, selection, selectionArgs);
    }
}
