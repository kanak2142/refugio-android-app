package com.example.refugio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoodDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Refugio.db";
    private static final int DATABASE_VERSION = 3;

    public MoodDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String userTable = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT)";

        db.execSQL(userTable);

        String moodTable = "CREATE TABLE mood_entries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mood TEXT, " +
                "anxiety INTEGER, " +
                "trigger_text TEXT, " +
                "note TEXT, " +
                "date TEXT)";

        db.execSQL(moodTable);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        if (oldVersion < 3) {

            String userTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "email TEXT UNIQUE, " +
                    "password TEXT)";

            db.execSQL(userTable);
        }
    }
}