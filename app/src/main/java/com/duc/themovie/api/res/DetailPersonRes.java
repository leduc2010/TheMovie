package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailPersonRes {
    @SerializedName("name")
    public String name;
    @SerializedName("profile_path")
    public String profilePath;
    @SerializedName("gender")
    public int gender;
    @SerializedName("birthday")
    public String birthday;
    @SerializedName("place_of_birth")
    public String placeOfBirth;
    @SerializedName("biography")
    public String biography;
    @SerializedName("known_for_department")
    public String knownForDepartment;
    @SerializedName("imdb_id")
    public String imdbId;
    @SerializedName("cast")
    public List<Cast> cast;

    public static class Cast {
        @SerializedName("media_type")
        public String mediaType;

        @SerializedName("release_date")
        public String releaseDate;

        @SerializedName("character")
        public String character;

        @SerializedName("title")
        public String title;

        @SerializedName("name")
        public String name;
    }
}
