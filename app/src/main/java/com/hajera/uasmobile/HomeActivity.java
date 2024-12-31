package com.hajera.uasmobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Pastikan ini sesuai dengan layout yang benar

        // Inisialisasi RecyclerView setelah setContentView
        recyclerView = findViewById(R.id.recyclerView);
        if (recyclerView == null) {
            Log.e("HomeActivity", "RecyclerView not found in layout!");
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Grid layout dengan 2 kolom
        }

        // Inisialisasi BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView == null) {
            Log.e("HomeActivity", "BottomNavigationView is not found in layout!");
        } else {
            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.navigation_home) {
                    selectedFragment = new HomeFragment(); // Load HomeFragment
                } else if (item.getItemId() == R.id.navigation_library) {
                    selectedFragment = new LibraryFragment(); // Load LibraryFragment
                } else if (item.getItemId() == R.id.navigation_account) {
                    selectedFragment = new AccountFragment(); // Load AccountFragment
                }

                // Memuat fragment jika dipilih
                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                    return true;
                }

                return false;
            });
        }

        // Memuat HomeFragment saat Activity dimulai
        loadFragment(new HomeFragment());

        // Mengambil Movies dari API
        fetchMovies();
    }

    // Method untuk memuat fragment
    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment) // Replace dengan fragment yang dipilih
                    .commit();
        }
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
                        if (recyclerView != null) {
                            movieAdapter = new MovieAdapter(movies, HomeActivity.this);
                            recyclerView.setAdapter(movieAdapter); // Set adapter ke RecyclerView
                        } else {
                            Log.e("HomeActivity", "RecyclerView is null");
                        }
                    }
                } else {
                    Log.e("HomeActivity", "Failed to get movie data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("HomeActivity", "Error fetching movies: " + t.getMessage());
            }
        });
    }
}
