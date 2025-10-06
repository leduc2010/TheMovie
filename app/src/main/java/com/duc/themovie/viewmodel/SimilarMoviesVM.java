package com.duc.themovie.viewmodel;

import com.duc.themovie.api.res.MovieRes;
import com.duc.themovie.api.res.TVShowRes;

import java.util.ArrayList;
import java.util.List;

public class SimilarMoviesVM extends BaseViewModel {

    public static final String KEY_GET_SIMILAR_MOVIES = "KEY_GET_SIMILAR_MOVIES";
    public static final String KEY_GET_SIMILAR_TVSHOWS = "KEY_GET_SIMILAR_TVSHOWS";
    private final List<MovieRes.Result> similarMovieList = new ArrayList<>();
    private final List<TVShowRes.Result> similarTVShowList = new ArrayList<>();

    public void getSimilarMovies(int itemId) {
        getAPI().getSimilarMovies(itemId).enqueue(initHandleResponse(KEY_GET_SIMILAR_MOVIES));
    }

    public void getSimilarTVShows(int itemId) {
        getAPI().getSimilarTVs(itemId).enqueue(initHandleResponse(KEY_GET_SIMILAR_TVSHOWS));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (key.equals(KEY_GET_SIMILAR_MOVIES)) {
            MovieRes res = (MovieRes) body;
            similarMovieList.clear();
            similarMovieList.addAll(res.resultsMovie);
        }else if (key.equals(KEY_GET_SIMILAR_TVSHOWS)){
            TVShowRes res = (TVShowRes) body;
            similarTVShowList.clear();
            similarTVShowList.addAll(res.resultsTVShow);
        }
        super.handleSuccess(key, body);
    }

    public List<MovieRes.Result> getSimilarMovieList() {
        return similarMovieList;
    }

    public List<TVShowRes.Result> getSimilarTVShowList() {
        return similarTVShowList;
    }
}
