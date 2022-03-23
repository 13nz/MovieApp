package com.example.movieapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.models.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<Review> reviews;

    public  ReviewAdapter(Context context, ArrayList<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    public  ReviewAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.review_item, parent, false);
        ReviewAdapter.MyViewHolder item = new ReviewAdapter.MyViewHolder(row);

        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).txtvwTitle.setText(reviews.get(position).getTitle());
        ((MyViewHolder)holder).txtvwBody.setText(reviews.get(position).getBody());
        ((MyViewHolder)holder).txtvwRating.setText(String.valueOf(reviews.get(position).getRating()));
        String imagePath = reviews.get(position).getImage();
        if (imagePath.startsWith("https")) {
            Picasso.get().load(reviews.get(position).getImage()).into(((MyViewHolder) holder).imgvwPic);
        } else {
            Bitmap bitmap = new ImageSaver(context).
                    setFileName("img"+reviews.get(position).getTitle()).
                    setDirectoryName("images").
                    load();

            ((MyViewHolder)holder).imgvwPic.setImageBitmap(bitmap);
        }


        holder.itemView.setOnClickListener(view -> {
            Review review = reviews.get(position);
            int id = review.getId();


            Intent intent = new Intent(view.getContext(), ReviewActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent);



        });

    }

    @Override
    public int getItemCount() {
        return reviews != null ? reviews.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgvwPic;
        TextView txtvwTitle;
        TextView txtvwRating;
        TextView txtvwBody;

        public MyViewHolder(View view) {
            super(view);
            imgvwPic = view.findViewById(R.id.imgvwPic);
            txtvwTitle = view.findViewById(R.id.txtvwTitle);
            txtvwRating = view.findViewById(R.id.txtvwRating);
            txtvwBody = view.findViewById(R.id.txtvwBody);
        }
    }
}
