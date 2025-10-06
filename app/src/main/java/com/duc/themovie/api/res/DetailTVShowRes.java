package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailTVShowRes {
    @SerializedName("status")
    public String status;
    @SerializedName("runtime")
    public int runtime;
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

    @SerializedName("name")
    public String name;

    @SerializedName("first_air_date")
    public String firstAirDate;

    @SerializedName("genres")
    public List<Genre> genres;

    @SerializedName("type")
    public String type;
    @SerializedName("number_of_seasons")
    public int numberOfSeasons;
    @SerializedName("number_of_episodes")
    public int numberOfEpisodes;
    @SerializedName("production_companies")
    public List<ProductionCompany> productionCompanies;
    @SerializedName("production_countries")
    public List<ProductionCountry> productionCountries;

    @SerializedName("networks")
    public List<Network> networks;

    @SerializedName("create_by")
    public List<Creator> createdBy;


    public static class Genre {
        @SerializedName("name")
        public String name;
    }

    public static class ProductionCompany {
        @SerializedName("name")
        public String name;
    }

    public static class ProductionCountry {
        @SerializedName("name")
        public String name;
    }

    public static class Creator {
        @SerializedName("name")
        public String name;
    }

    public static class Network {
        @SerializedName("logo_path")
        public String logoPath;
    }
}
