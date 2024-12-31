package com.hajera.uasmobile;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class LibraryResponse {
    @SerializedName("results")
    private List<Library> library;

    public List<Library> getLibrary() {
        return library;
    }
}
