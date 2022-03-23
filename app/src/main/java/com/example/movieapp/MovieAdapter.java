package com.example.movieapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<Movie> movies;

    public  MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    public  MovieAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.movie_item, parent, false);
        MyViewHolder item = new MyViewHolder(row);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).movieTitle.setText(movies.get(position).getTitle());
        ((MyViewHolder)holder).movieYear.setText(movies.get(position).getReleaseYear());
        Picasso.get().load(movies.get(position).getImage()).into(((MyViewHolder) holder).moviePoster);

        holder.itemView.setOnClickListener(view -> {
            Movie movie = movies.get(position);
            String id = movie.getImdbID();


            Intent intent = new Intent(view.getContext(), MovieActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        if(movies != null) {
            return this.movies.size();
        } else {
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle;
        TextView movieYear;

        public MyViewHolder(View view) {
            super(view);
            moviePoster = view.findViewById(R.id.moviePoster);
            movieTitle = view.findViewById(R.id.movieTitle);
            movieYear = view.findViewById(R.id.movieYear);
        }
    }
}
