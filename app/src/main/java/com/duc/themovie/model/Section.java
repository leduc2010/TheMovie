package com.duc.themovie.model;

import com.duc.themovie.api.res.MovieRes;
import com.duc.themovie.api.res.TVShowRes;

import java.util.List;

public class Section {
    private String title;
    private List<MovieRes.Result> movies;
    private List<TVShowRes.Result> tvShows;

    private String type;

    public List<TVShowRes.Result> getTvShows() {
        return tvShows;
    }

    public List<MovieRes.Result> getMovies() {
        return movies;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public Section(String title, List<MovieRes.Result> movies, List<TVShowRes.Result> tvShows, String type) {
        this.title = title;
        this.movies = movies;
        this.tvShows = tvShows;
        this.type = type;
    }
}
