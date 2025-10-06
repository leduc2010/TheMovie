package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ImageRes implements Serializable {
    public List<Backdrop> backdrops;
    public List<Profile> profiles;
    public List<Poster> posters;

    public static class Backdrop implements Serializable {
        @SerializedName("file_path")
        public String filePath;
    }

    public static class Poster implements Serializable {
        @SerializedName("file_path")
        public String filePath;
    }

    public static class Profile implements Serializable {
        @SerializedName("file_path")
        public String filePath;
    }
}
