package com.duc.themovie.viewmodel;

import com.duc.themovie.api.res.MultiSearchRes;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends BaseViewModel {

    public static final String KEY_SEARCH_MULTI = "KEY_SEARCH_MULTI";

    private final List<MultiSearchRes.Result> listMovies = new ArrayList<>();
    private final List<MultiSearchRes.Result> listTVShows = new ArrayList<>();
    private final List<MultiSearchRes.Result> listPeople = new ArrayList<>();

    public List<MultiSearchRes.Result> getListMovies() {
        return listMovies;
    }

    public List<MultiSearchRes.Result> getListTVShows() {
        return listTVShows;
    }

    public List<MultiSearchRes.Result> getListPeople() {
        return listPeople;
    }

    public void searchMulti(String query) {
        getAPI().searchMulti(query).enqueue(initHandleResponse(KEY_SEARCH_MULTI));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (key.equals(KEY_SEARCH_MULTI)) {
            MultiSearchRes res = (MultiSearchRes) body;

            // Clear old data
            listMovies.clear();
            listTVShows.clear();
            listPeople.clear();

            // Phân loại kết quả
            for (MultiSearchRes.Result result : res.results) {
                switch (result.mediaType) {
                    case "movie":
                        MultiSearchRes.Result movie = new MultiSearchRes.Result();
                        movie.id = result.id;
                        movie.title = result.title;
                        movie.posterPath = result.posterPath;
                        movie.voteAverage = result.voteAverage;
                        movie.releaseDate = result.releaseDate;
                        movie.mediaType = "movie";
                        listMovies.add(movie);
                        break;

                    case "tv":
                        MultiSearchRes.Result tv = new MultiSearchRes.Result();
                        tv.id = result.id;
                        tv.title = result.name;
                        tv.posterPath = result.posterPath;
                        tv.voteAverage = result.voteAverage;
                        tv.releaseDate = result.firstAirDate;
                        tv.mediaType = "tv";
                        listTVShows.add(tv);
                        break;

                    case "person":
                        MultiSearchRes.Result person = new MultiSearchRes.Result();
                        person.id = result.id;
                        person.title = result.name;
                        person.profilePath = result.profilePath;
                        person.knownForDepartment = result.knownForDepartment;
                        person.mediaType = "person";
                        listPeople.add(person);
                        break;
                }
            }
        }
        super.handleSuccess(key, body);
    }
}