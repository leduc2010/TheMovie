package com.duc.themovie.api.res;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideoRes implements Serializable {
    @SerializedName("results")
    public List<Result> results;

    public static class Result {
        @SerializedName("id")
        public String id;
        @SerializedName("name")
        public String name;
        @SerializedName("key")
        public String key;
        @SerializedName("published_at")
        public String publishAt;
    }
}
