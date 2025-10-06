package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PeopleRes implements Serializable {
    @SerializedName("page")
    public int page;
    @SerializedName("results")
    public List<Result> results;

    public static class Result {
        @SerializedName("adult")
        public boolean adult;
        @SerializedName("gender")
        public int gender;
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("known_for")
        public List<KnownFor> knownFor;
        @SerializedName("profile_path")
        public String profilePath;
        @SerializedName("known_for_department")
        public String knownForDepartment;

        public static class KnownFor {
            @SerializedName("name")
            public String name;
            @SerializedName("title")
            public String title;
            @SerializedName("first_air_date")
            public String firstAirDate;
            @SerializedName("release_date")
            public String releaseDate;
            @SerializedName("media_type")
            public String mediaType;
        }
    }

}
