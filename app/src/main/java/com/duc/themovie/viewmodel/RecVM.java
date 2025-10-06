package com.duc.themovie.viewmodel;

import com.duc.themovie.api.res.MovieRes;
import com.duc.themovie.api.res.TVShowRes;

import java.util.ArrayList;
import java.util.List;

public class RecVM extends BaseViewModel {
    private final List<MovieRes.Result> recMovieList = new ArrayList<>();
    private final List<TVShowRes.Result> recTVSHowList = new ArrayList<>();
    public static final String KEY_GET_RECOMMENDATION_TVSHOWS = "KEY_GET_RECOMMENDATION_TVSHOWS";

    public static final String KEY_GET_RECOMMENDATION_MOVIES = "KEY_GET_RECOMMENDATION_MOVIES";

    public List<MovieRes.Result> getRecMovieList() {
        return recMovieList;
    }

    public List<TVShowRes.Result> getRecTVSHowList() {
        return recTVSHowList;
    }

    public void getRecMovies(int itemId) {
        getAPI().getRecommendationMovies(itemId).enqueue(initHandleResponse(KEY_GET_RECOMMENDATION_MOVIES));
    }

    public void getRecTVShows(int itemId) {
        getAPI().getRecommendationTVs(itemId).enqueue(initHandleResponse(KEY_GET_RECOMMENDATION_TVSHOWS));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (key.equals(KEY_GET_RECOMMENDATION_MOVIES)) {
            MovieRes res = (MovieRes) body;
            recMovieList.clear();
            recMovieList.addAll(res.resultsMovie);
        } else if (key.equals(KEY_GET_RECOMMENDATION_TVSHOWS)) {
            TVShowRes res = (TVShowRes) body;
            recTVSHowList.clear();
            recTVSHowList.addAll(res.resultsTVShow);
        }
        super.handleSuccess(key, body);
    }

}
