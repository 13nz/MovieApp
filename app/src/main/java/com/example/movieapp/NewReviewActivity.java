package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.android.car.ui.AlertDialogBuilder;
import com.example.movieapp.models.Review;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class NewReviewActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;

    private EditText edtxtTitle;
    private EditText edtxtBody;
    private ImageView imageView;
    private Button imgBtn;
    private Spinner spinner;
    private RatingBar ratingBar;
    private EditText editTextDate;
    private Button saveBtn, btnGenres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        //Log.d("LATEST", String.valueOf(Review.getLatestID(this)));

        edtxtTitle = findViewById(R.id.edtxtTitle);
        edtxtBody = findViewById(R.id.edtxtBody);
        imageView = findViewById(R.id.imageView);
        imgBtn = findViewById(R.id.imageBtn);
        //spinner = findViewById(R.id.spinner);
        ratingBar = findViewById(R.id.ratingBar);
        editTextDate = findViewById(R.id.editTextDate);
        saveBtn = findViewById(R.id.saveBtn);
        btnGenres = findViewById(R.id.btnGenres);

        if (getIntent().getExtras() != null) {
            edtxtTitle.setText(getIntent().getExtras().getString("title"));
            Picasso.get().load(getIntent().getExtras().getString("image")).into(imageView);

        }

        /*
        String genres[] = new String[]{"Action", "Comedy", "Drama", "Fantasy", "Horror", "Mystery", "Romance", "Thriller", "Western"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, genres);
        spinner.setAdapter(adapter);

         */


        imgBtn.setOnClickListener(view -> {
            imageChooser();
        });

        ArrayList<String> genres = new ArrayList<>();

        btnGenres.setOnClickListener(view -> {
            // Set up the alert builder
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select genres");

            // Add a checkbox list
            String[] genresList = {"Action", "Comedy", "Drama", "Fantasy", "Horror", "Mystery", "Romance", "Thriller", "Western"};
            boolean[] checkedItems = {false, false, false, false, false, false, false, false, false};
            builder.setMultiChoiceItems(genresList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    checkedItems[which] = isChecked;

                    String currentItem = genresList[which];
                    if (!genres.contains(currentItem)) {
                        genres.add(currentItem);
                    }
                }
            });

            // Add OK and Cancel buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("GENRES", genres.toString());
                    return;
                }
            });
            builder.setNegativeButton("Cancel", null);

            // Create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();


        });


        saveBtn.setOnClickListener(view -> {
            String title = edtxtTitle.getText().toString();
            String body = edtxtBody.getText().toString();
            //String genre = spinner.getSelectedItem().toString();
            String date = editTextDate.getText().toString();
            int rating = (int) ratingBar.getRating();
            String image = "";
            if (getIntent().getExtras() == null) {
                Uri uri = Uri.parse(imageView.getTag().toString());
                Bitmap bitmap = null;

                try {
                    bitmap = useImage(uri);
                    new ImageSaver(getApplicationContext()).
                            setFileName("img" + title).
                            setDirectoryName("images").
                            save(bitmap);
                    image = "images/img" + title;
                } catch (IOException e) {
                    e.printStackTrace();
                }} else {
                    image = getIntent().getExtras().getString("image");
                }

                if(!title.isEmpty() && !body.isEmpty()/*&& !genre.isEmpty()*/ && !date.isEmpty() &&!image.isEmpty() && !genres.isEmpty()) {

                    Review review = new Review();
                    review.setTitle(title);
                    review.setBody(body);
                    review.setDate(date);
                    //review.setGenre(genre);
                    review.setRating(rating);
                    review.setImage(image);
                    ArrayList<Integer> genresID = new ArrayList<>();

                    for (String genre : genres) {
                        int id = getGenreId(genre);
                        genresID.add(id);
                    }

                    review.save(NewReviewActivity.this);


                    for(int id : genresID) {
                        Review.saveGenres(this, id);
                        Log.d("GENRE SAVED", String.valueOf(id));
                    }


                    NewReviewActivity.this.finish();
                }

        });

    }

    public void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        startActivityForResult(Intent.createChooser(intent, "Select an image"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();

                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageView.setImageURI(selectedImageUri);
                    imageView.setTag(selectedImageUri.toString());

                }
            }
        }
    }


    Bitmap useImage(Uri uri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        //use the bitmap as you like
        //Drawable d = new BitmapDrawable(getResources(), bitmap);
        imageView.setImageBitmap(bitmap);
        return bitmap;
    }

    int getGenreId(String genre) {
        int id;
        switch(genre) {
            case "Action":
                id = 0;
                break;
            case "Comedy":
                id = 1;
                break;
            case "Drama":
                id = 2;
                break;
            case "Fantasy":
                id = 3;
                break;
            case "Horror":
                id = 4;
                break;
            case "Mystery":
                id = 5;
                break;
            case "Romance":
                id = 6;
                break;
            case "Thriller":
                id = 7;
                break;
            case "Western":
                id = 8;
                break;
            default:
                id = 0;
                break;
        }
        return id;
    }

}