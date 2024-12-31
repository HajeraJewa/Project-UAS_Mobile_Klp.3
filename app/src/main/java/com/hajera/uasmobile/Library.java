package com.hajera.uasmobile;

import com.google.gson.annotations.SerializedName;

public class Library {
        @SerializedName("title")
        private String title;

        @SerializedName("overview")
        private String description;

        @SerializedName("poster_path")
        private String posterPath;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getPosterPath() {
            return posterPath;
        }
    }

