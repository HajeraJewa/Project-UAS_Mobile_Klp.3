package com.hajera.uasmobile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    private List<Movie> movies;
    private List<Library> library;

    public List<Movie> getMovies() {
        return movies;
    }
}
