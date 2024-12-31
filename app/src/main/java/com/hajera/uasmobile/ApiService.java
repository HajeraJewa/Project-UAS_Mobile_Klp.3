package com.hajera.uasmobile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    // Endpoint untuk mendapatkan daftar film populer
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    // Endpoint untuk mendapatkan daftar library populer
    @GET("library/popular")
    Call<LibraryResponse> getRecentLibrary(@Query("api_key") String apiKey);
}

