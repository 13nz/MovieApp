package com.example.movieapp.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.movieapp.SQLiteHelper;

import java.util.ArrayList;

public class Review {
    private int id;
    private String title;
    private String date;
    private String body;
    private ArrayList<String> genres;
    private String image;
    private int rating;

    public Review() {

    }

    public Review(Context context, int id) {
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM reviews WHERE id = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();

        this.id = id;
        this.title = cursor.getString(1);
        this.rating = cursor.getInt(2);
        this.date = cursor.getString(3);
        //this.genre = cursor.getString(4);
        this.genres = this.getGenres(context);
        this.body = cursor.getString(4);
        this.image = cursor.getString(5);
        cursor.close();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /*
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

     */

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public void save(Context context) {
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();



        ContentValues values = new ContentValues();
        values.put("title", this.title);
        values.put("body", this.body);
        values.put("image", this.image);
        //values.put("genre", this.genre);
        values.put("rating", this.rating);
        values.put("date", this.date);

        database.insert("reviews", null, values);
    }

    public static void saveGenres(Context context, int genre_id) {
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("genre_id", genre_id);
        values.put("review_id", getLatestID(context));
        database.insert("genres_reviews", null, values);
    }

    public static ArrayList<Review> getAll(Context context) {
        ArrayList<Review> reviews = new ArrayList<>();

        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.query("reviews", new String[]{"id", "title", "rating", "date", "body", "image"}, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Review review = new Review();

            review.setId(cursor.getInt(0));
            review.setTitle(cursor.getString(1));
            review.setRating(cursor.getInt(2));
            review.setDate(cursor.getString(3));
            review.setBody(cursor.getString(4));
            review.setImage(cursor.getString(5));

            reviews.add(review);
            cursor.moveToNext();
        }
        cursor.close();
        return reviews;
    }


    public void delete(Context context) {
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        database.delete("reviews", "id=?", new String[] {String.valueOf(this.id)});
        database.delete("genres_reviews", "review_id=?", new String[]{String.valueOf(this.id)});
    }

    public ArrayList<String> getGenres(Context context) {
        ArrayList<String> reviewGenres = new ArrayList<>();
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();


        Cursor cursor = database.rawQuery("SELECT name FROM genres LEFT JOIN genres_reviews ON genres.id=genres_reviews.genre_id WHERE genres_reviews.review_id=?", new String[]{String.valueOf(this.id)});
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String genre = cursor.getString(0);

            reviewGenres.add(genre);
            cursor.moveToNext();
        }
        cursor.close();

        return reviewGenres;
    }

    public static int getLatestID(Context context) {
        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM reviews", null);
        cursor.moveToLast();
        int id = cursor.getInt(0);

        return id;
    }

    public static ArrayList<Review> searchGenre(Context context, int genre_id) {
        ArrayList<Review> reviews = new ArrayList<>();

        SQLiteHelper helper = new SQLiteHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM genres_reviews WHERE genre_id = ?", new String[]{String.valueOf(genre_id)});
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Review review = new Review(context, cursor.getInt(2));

            reviews.add(review);
            cursor.moveToNext();
        }
        cursor.close();
        return reviews;
    }

}
