package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MovieActivity extends AppCompatActivity {

    ImageView poster;
    TextView title, year, director, writers, actors, plot;
    Button review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        poster = findViewById(R.id.imgvwPoster);
        title = findViewById(R.id.txtvwMovieTitle);
        year = findViewById(R.id.txtvwYear);
        director = findViewById(R.id.txtvwDirector);
        writers = findViewById(R.id.txtvwWriters);
        actors = findViewById(R.id.txtvwActors);
        plot = findViewById(R.id.txtvwPlot);
        review = findViewById(R.id.btnReview);


        String id = getIntent().getExtras().getString("id");
        //Log.d("ID", id);

        RequestQueue queue = Volley.newRequestQueue(MovieActivity.this);
        String key = getResources().getResourceName(R.string.key1);


        String url = "https://www.omdbapi.com/?apikey=" + BuildConfig.OMDB_API_KEY + "&i=" + id;

        //Log.d("URL", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Picasso.get().load(response.getString("Poster")).into(poster);
                    title.setText(response.getString("Title"));
                    year.setText(response.getString("Year"));
                    director.setText(response.getString("Director"));
                    writers.setText(response.getString("Writer"));
                    actors.setText(response.getString("Actors"));
                    plot.setText(response.getString("Plot"));

                    review.setOnClickListener(view -> {
                        Intent intent = new Intent(MovieActivity.this, NewReviewActivity.class);
                        try {
                            intent.putExtra("title", response.getString("Title"));
                            intent.putExtra("image", response.getString("Poster"));

                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
            }
        });

        queue.add(request);
    }
}