package com.example.movieapp.ui.search;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.Movie;
import com.example.movieapp.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchViewModel extends ViewModel {


    public SearchViewModel() {
    }

    public static MovieAdapter getSearchAdapter(Context context, ArrayList<Movie> movies) {
        return new MovieAdapter(context, movies);
    }

    public static ArrayList<Movie> getMovies(Context context, JSONArray results) throws JSONException {
        ArrayList<Movie> movies = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            Movie movie = new Movie();
            JSONObject object = results.getJSONObject(i);

            movie.setImdbID(object.getString("id"));
            movie.setImage(object.getString("image"));
            movie.setTitle(object.getString("title"));
            movie.setReleaseYear(object.getString("description"));

            movies.add(movie);
        }

        return movies;
    }

}