package com.example.movieapp.models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movie {
    private String imdbID;
    private String title;
    private String image;
    private String plot;
    private String releaseYear;

    public Movie() {}

    public Movie(String id, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://www.omdbapi.com/?apikey=" + BuildConfig.OMDB_API_KEY + "&i=" + id;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    imdbID = imdbID;
                    title = response.getString("Title");
                    image = response.getString("Poster");
                    plot = response.getString("Plot");
                    releaseYear = response.getString("Year");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public static ArrayList<Movie> getAllInTheatres(JSONObject object) {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            JSONArray array = object.getJSONArray("items");

            for (int i = 0; i < array.length(); i++) {
                Movie movie = new Movie();
                JSONObject item = array.getJSONObject(i);

                movie.setImdbID(item.getString("id"));
                movie.setImage(item.getString("image"));
                movie.setTitle(item.getString("title"));
                movie.setReleaseYear(item.getString("year"));

                movies.add(movie);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

    public static ArrayList<Movie> getAllSearch(String s, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://www.omdbapi.com/?apikey=" + BuildConfig.OMDB_API_KEY + "&t=" + s;
        ArrayList<Movie> movies = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        String id = response.getString("imdbID");
                        Movie movie = new Movie(id, context);

                        movies.add(movie);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
        return movies;
    }

    public static Movie getMovie(JSONObject object) throws JSONException {
        Movie movie = new Movie();

        movie.imdbID = object.getString("imdbID");
        movie.title = object.getString("Title");
        movie.image = object.getString("Poster");
        movie.plot = object.getString("Plot");
        movie.releaseYear = object.getString("Year");

        return movie;
    }
}
