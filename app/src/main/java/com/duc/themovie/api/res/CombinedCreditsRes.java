package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CombinedCreditsRes {
    @SerializedName("cast")
    public List<Cast> listCast;

    @SerializedName("crew")
    public List<Crew> listCrew;

    public static class Cast {
        @SerializedName("name")
        public String name;
        @SerializedName("character")
        public String character;
        @SerializedName("title")   // cho movie
        public String title;

        @SerializedName("media_type")
        public String mediaType;
        @SerializedName("release_date")    // cho movie
        public String releaseDate;
        @SerializedName("first_air_date")
        public String firstAirDate;
    }

    public static class Crew {
        @SerializedName("department")
        public String department;
        @SerializedName("media_type")
        public String mediaType;
        @SerializedName("job")
        public String job;
    }
}
