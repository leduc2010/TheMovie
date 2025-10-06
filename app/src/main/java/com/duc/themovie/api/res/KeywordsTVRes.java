package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class KeywordsTVRes implements Serializable {
    @SerializedName("results")
    public List<Keyword> results;


    public static class Keyword implements Serializable {
        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;
    }
}
