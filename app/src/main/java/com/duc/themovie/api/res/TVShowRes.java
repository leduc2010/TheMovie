package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TVShowRes implements Serializable {
    @SerializedName("page")
    public int page;
    @SerializedName("total_pages")
    public int totalPages;
    @SerializedName("results")
    public List<Result> resultsTVShow;
    @SerializedName("total_results")
    public int totalResults;

    public static class Result implements Serializable {
        @SerializedName("adult")
        public boolean adult;
        @SerializedName("backdrop_path")
        public String backdropPath;
        @SerializedName("id")
        public int id;
        @SerializedName("original_name")
        public String name;
        @SerializedName("overview")
        public String overview;
        @SerializedName("poster_path")
        public String posterPath;
        @SerializedName("first_air_date")
        public String firstAirDate;
        @SerializedName("vote_average")
        public Double voteAverage;

    }
}
