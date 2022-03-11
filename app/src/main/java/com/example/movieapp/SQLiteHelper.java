package com.example.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reviews_db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null,  DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE reviews (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, rating int NOT NULL, date TEXT NOT NULL, /*genre TEXT NOT NULL,*/ body TEXT NOT NULL, image TEXT NOT NULL)");
        db.execSQL("CREATE TABLE genres (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)");
        db.execSQL("CREATE TABLE genres_reviews (id INTEGER PRIMARY KEY AUTOINCREMENT, genre_id INTEGER, review_id INTEGER)");

        String[] genres = {"Action", "Comedy", "Drama", "Fantasy", "Horror", "Mystery", "Romance", "Thriller", "Western"};

        for (String genre : genres) {
            ContentValues values = new ContentValues();
            values.put("name", genre);
            db.insert("genres", null, values);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
