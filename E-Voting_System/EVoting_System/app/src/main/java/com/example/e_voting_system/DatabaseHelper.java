package com.example.e_voting_system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "e_voting_db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USER_CREDENTIAL = "CREATE TABLE UserCredential (" +
            "Email TEXT PRIMARY KEY, " +
            "Name TEXT, " +
            "Password TEXT, " +
            "Recovery_Email TEXT)";


    private static final String CREATE_TABLE_RESULTS = "CREATE TABLE Results (" +
            "Party_Name TEXT PRIMARY KEY, " +
            "Votes INTEGER)";

    private static final String CREATE_TABLE_VOTER_DETAILS = "CREATE TABLE VoterDetails (" +
            "CNIC TEXT PRIMARY KEY, " +
            "First_Name TEXT, " +
            "Last_Name TEXT, " +
            "Father_Name TEXT, " +
            "DOB DATE, " +
            "Country TEXT, " +
            "Province TEXT, " +
            "City TEXT, " +
            "House_Address TEXT, " +
            "Vote_Status INT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER_CREDENTIAL);
        db.execSQL(CREATE_TABLE_RESULTS);
        db.execSQL(CREATE_TABLE_VOTER_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS UserCredential");
        db.execSQL("DROP TABLE IF EXISTS Results");
        db.execSQL("DROP TABLE IF EXISTS VoterDetails");

        // Create tables again
        onCreate(db);
    }
}
