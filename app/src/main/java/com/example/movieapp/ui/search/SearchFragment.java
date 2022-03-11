package com.example.movieapp.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieapp.BuildConfig;
import com.example.movieapp.models.Movie;
import com.example.movieapp.MovieAdapter;
import com.example.movieapp.databinding.FragmentSearchBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.recyclerSearch.setAdapter(new MovieAdapter(getContext()));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText searchBar = binding.searchBar;
        RecyclerView recycler = binding.recyclerSearch;
        Button button = binding.button;

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recycler.setLayoutManager(layoutManager);

        button.setOnClickListener(view1 -> {
            String s = searchBar.getText().toString();
            Log.d("SEARCH", s);

            RequestQueue queue = Volley.newRequestQueue(getContext());
            String url = "https://imdb-api.com/API/Search/" + BuildConfig.IMDB_API_KEY2 + "/" + s;

            Log.d("URL", url);



            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray array = response.getJSONArray("results");
                        ArrayList<Movie> movies = SearchViewModel.getMovies(getContext(), array);
                        Log.d("MOVIES", String.valueOf(movies.size()));
                        binding.recyclerSearch.setAdapter(SearchViewModel.getSearchAdapter(getContext(), movies));
                        Log.d("SEARCH", binding.recyclerSearch.toString());
                    } catch (JSONException e) {
                        Log.d("ERROR", "ERROR");
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            queue.add(request);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}