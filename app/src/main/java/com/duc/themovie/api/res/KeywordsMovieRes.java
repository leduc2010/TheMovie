package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class KeywordsMovieRes implements Serializable {
    @SerializedName("keywords")
    public List<Keyword> keywords;

    public static class Keyword implements Serializable {
        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;
    }
}
