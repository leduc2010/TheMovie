package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CreditRes implements Serializable {
    @SerializedName("cast")
    public List<Cast> Casts;


    public static class Cast implements Serializable {
        @SerializedName("original_name")
        public String originalName;
        @SerializedName("title")
        public String title;
        @SerializedName("character")
        public String character;
        @SerializedName("profile_path")
        public String profilePath;
    }
}
