package com.example.movieapp.ui.reviews;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.movieapp.models.Review;
import com.example.movieapp.ReviewAdapter;

import java.util.ArrayList;

public class ReviewsViewModel extends ViewModel {

    public ReviewsViewModel() {
    }

    public static ReviewAdapter getAdapter(Context context) {
        ArrayList<Review> reviews = Review.getAll(context);

        return new ReviewAdapter(context, reviews);
    }

    public static ReviewAdapter getGenreAdapter(Context context, int genre) {
        ArrayList<Review> reviews = Review.searchGenre(context, genre);

        return new ReviewAdapter(context, reviews);
    }

}