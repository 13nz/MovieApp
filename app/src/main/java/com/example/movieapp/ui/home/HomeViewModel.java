package com.example.movieapp.ui.home;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.Movie;
import com.example.movieapp.MovieAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {


    public HomeViewModel() {

    }

    public static MovieAdapter getAdapter(Context context, JSONObject object) {
        ArrayList<Movie> movies = Movie.getAllInTheatres(object);

        return new MovieAdapter(context, movies);



    }
}