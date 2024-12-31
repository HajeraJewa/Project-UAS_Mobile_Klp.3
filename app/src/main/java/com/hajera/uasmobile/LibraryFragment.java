package com.hajera.uasmobile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {

    private RecyclerView recyclerView;
    private LibraryAdapter libraryAdapter;

    public LibraryFragment() {
        // Default constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Connect the layout fragment_library.xml
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.libraryRecyclerView);
        if (recyclerView == null) {
            Log.e("LibraryFragment", "RecyclerView not found in layout!");
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid with 2 columns
        }

        // Fetch library data from API
        fetchLibraryData();

        return view;
    }

    // Method to fetch library data
    private void fetchLibraryData() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        String apiKey = "39b7349a4857966804a3321a00ffab64"; // Replace with a valid API key

        Call<LibraryResponse> call = apiService.getRecentLibrary(apiKey);
        call.enqueue(new Callback<LibraryResponse>() {
            @Override
            public void onResponse(Call<LibraryResponse> call, Response<LibraryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Library> libraryList = response.body().getLibrary();
                    setupRecyclerView(libraryList);
                } else {
                    Log.e("LibraryFragment", "Error: " + response.message());
                    Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LibraryResponse> call, Throwable t) {
                Log.e("LibraryFragment", "API call failed: " + t.getMessage());
                Toast.makeText(getContext(), "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to setup RecyclerView with data
    private void setupRecyclerView(List<Library> libraryList) {
        libraryAdapter = new LibraryAdapter(libraryList, getContext());
        recyclerView.setAdapter(libraryAdapter);
    }
}
