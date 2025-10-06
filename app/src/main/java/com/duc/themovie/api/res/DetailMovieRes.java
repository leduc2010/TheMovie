package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailMovieRes {
    @SerializedName("status")
    public String status;
    @SerializedName("original_language")
    public String originalLanguage;
    @SerializedName("overview")
    public String overview;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("backdrop_path")
    public String backdropPath;
    @SerializedName("vote_average")
    public double voteAverage;
    @SerializedName("vote_count")
    public int voteCount;
    @SerializedName("title")
    public String title;
    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("runtime")
    public int runtime;
    @SerializedName("budget")
    public int budget;
    @SerializedName("revenue")
    public int revenue;

    @SerializedName("genres")
    public List<Genre> genres;

    @SerializedName("production_companies")
    public List<ProductionCompanies> productionCompanies;
    @SerializedName("production_countries")
    public List<ProductionCountry> productionCountries;

    public static class Genre {
        @SerializedName("name")
        public String name;
    }

    public static class ProductionCompanies {
        @SerializedName("name")
        public String name;
    }

    public static class ProductionCountry {
        @SerializedName("name")
        public String name;
    }
}
