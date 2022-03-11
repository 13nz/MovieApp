package com.example.movieapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.models.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        TextView txtTitle = findViewById(R.id.txtTitle);
        ImageView imgView = findViewById(R.id.imgView);
        TextView txtDate = findViewById(R.id.txtDate);
        TextView txtGenre = findViewById(R.id.txtGenre);
        TextView txtRating = findViewById(R.id.txtRating);
        TextView txtBody = findViewById(R.id.txtBody);
        Button btn = findViewById(R.id.btnDelete);


        int id = getIntent().getExtras().getInt("id");
        Log.d("REVIEW", String.valueOf(id));
        Review review = new Review(ReviewActivity.this, id);
        Log.d("REVIEW", review.getTitle());

        txtTitle.setText(review.getTitle());
        txtDate.setText(review.getDate());
        ArrayList<String> genres = review.getGenres(this);
        Log.d("GENRES", genres.toString());
        String listString = String.join(", ", genres);
        Log.d("GENRES STRING", listString);
        txtGenre.setText(listString);
        txtRating.setText(String.valueOf(review.getRating()));
        txtBody.setText(review.getBody());

        String image = review.getImage();

        if(image.substring(0, 5).equals("https")) {
            Picasso.get().load(image).into(imgView);
        } else {
            Bitmap bitmap = new ImageSaver(ReviewActivity.this).
                    setFileName("img" + review.getTitle()).
                    setDirectoryName("images").
                    load();
            imgView.setImageBitmap(bitmap);
        }

        btn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ReviewActivity.this);
            builder.setTitle("Delete review");
            builder.setMessage("Are you sure you want to delete this review?");
            builder.setCancelable(true);

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    review.delete(ReviewActivity.this);
                    ReviewActivity.this.finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });



    }
}