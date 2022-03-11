package com.example.movieapp.ui.reviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movieapp.NewReviewActivity;
import com.example.movieapp.R;
import com.example.movieapp.ReviewAdapter;
import com.example.movieapp.databinding.FragmentReviewsBinding;

public class ReviewsFragment extends Fragment {

    private FragmentReviewsBinding binding;
    private Button newReviewBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReviewsViewModel reviewsViewModel =
                new ViewModelProvider(this).get(ReviewsViewModel.class);

        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        binding.recyclerReviews.setLayoutManager(layoutManager);

        ReviewAdapter adapter = new ReviewAdapter(getContext());

        //ReviewAdapter adapter = ReviewsViewModel.getAdapter(view.getContext());

        binding.recyclerReviews.setAdapter(adapter);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, new String[]{"Action", "Comedy", "Drama", "Fantasy", "Horror", "Mystery", "Romance", "Thriller", "Western"});
        binding.spinnerGenres.setAdapter(spinnerAdapter);

        binding.spinnerGenres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.recyclerReviews.setAdapter(ReviewsViewModel.getGenreAdapter(getContext(), i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                binding.recyclerReviews.setAdapter(ReviewsViewModel.getAdapter(getContext()));
            }
        });

        binding.btnClear.setOnClickListener(view1 -> {
            binding.recyclerReviews.setAdapter(ReviewsViewModel.getAdapter(getContext()));
        });

        binding.addReviewBtn.setOnClickListener(view1 -> {
            //NavHostFragment.findNavController(ReviewsFragment.this).nav
            Intent intent = new Intent(view.getContext(), NewReviewActivity.class);
            startActivity(intent);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        ReviewAdapter adapter = ReviewsViewModel.getAdapter(getContext());

        binding.recyclerReviews.setAdapter(adapter);

    }
}