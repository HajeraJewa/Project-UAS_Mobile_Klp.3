package com.hajera.uasmobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    public HomeFragment() {
        // Default constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Menghubungkan layout fragment_home.xml
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inisialisasi RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        if (recyclerView == null) {
            Log.e("HomeFragment", "RecyclerView not found in layout!");
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid dengan 2 kolom
        }

        // Mengambil data film dari API
        fetchMovies();

        return view;
    }

    // Method untuk mengambil data film
    private void fetchMovies() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        String apiKey = "39b7349a4857966804a3321a00ffab64"; // Ganti dengan API Key yang valid

        // Mengambil data film populer
        apiService.getPopularMovies(apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getMovies(); // Mendapatkan daftar film
                    if (movies != null && !movies.isEmpty()) {
                        movieAdapter = new MovieAdapter(movies, getContext());
                        recyclerView.setAdapter(movieAdapter); // Set adapter ke RecyclerView
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("HomeFragment", "Error fetching movies: " + t.getMessage());
            }
        });
    }
}
