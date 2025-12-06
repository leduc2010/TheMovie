package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MultiSearchRes {

    @SerializedName("page")
    public int page;

    @SerializedName("results")
    public List<Result> results;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("total_results")
    public int totalResults;

    public static class Result {
        @SerializedName("id")
        public int id;

        @SerializedName("media_type")
        public String mediaType;

        // For movies
        @SerializedName("title")
        public String title;

        @SerializedName("release_date")
        public String releaseDate;

        // For TV shows
        @SerializedName("name")
        public String name;

        @SerializedName("first_air_date")
        public String firstAirDate;

        // Common
        @SerializedName("poster_path")
        public String posterPath;

        @SerializedName("vote_average")
        public float voteAverage;

        @SerializedName("overview")
        public String overview;

        @SerializedName("backdrop_path")
        public String backdropPath;

        // For people
        @SerializedName("profile_path")
        public String profilePath;

        @SerializedName("known_for_department")
        public String knownForDepartment;
    }
}