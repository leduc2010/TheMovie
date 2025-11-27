package com.duc.themovie.viewmodel;

import android.util.Log;

import com.duc.themovie.api.res.MovieRes;
import com.duc.themovie.api.res.TVShowRes;
import com.duc.themovie.model.Section;

import java.util.ArrayList;
import java.util.List;

public class HomeVM extends BaseViewModel {
    private static final String TAG = HomeVM.class.getName();
    public static final String KEY_GET_TRENDING_MOVIES = "KEY_GET_TRENDING_MOVIES";
    public static final String KEY_GET_POPULAR_MOVIES = "KEY_GET_POPULAR_MOVIES";
    public static final String KEY_GET_POPULAR_TVSHOWS = "KEY_GET_POPULAR_TVSHOWS";
    private int pageTrendingMovie = 0;
    private int pagePopularMovie = 0;
    private int pagePopularTVShow = 0;
    private List<Section> sections = new ArrayList<>();
    private String timeWindow = "day";
    private String type = "movie";
    private ArrayList<MovieRes.Result> resultMovieTrendingList = new ArrayList<>();
    private ArrayList<MovieRes.Result> resultMoviePopularList = new ArrayList<>();

    private ArrayList<TVShowRes.Result> resultTVShowList = new ArrayList<>();
    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow;
    }

    public String getTimeWindow() {
        return timeWindow;
    }

    public void getListTrendingMovies() {
        pageTrendingMovie++;
        Log.i(TAG, "Call API trending: timeWindow=" + timeWindow + ", page=" + pageTrendingMovie);
        getAPI().getTrendingMovies(timeWindow, pageTrendingMovie)
                .enqueue(initHandleResponse(KEY_GET_TRENDING_MOVIES));
    }

    public void getListPopularTVShows() {
        pagePopularTVShow++;
        Log.i(TAG, "Call API trending: type=" + type + ", page=" + pagePopularTVShow);
        getAPI().getPopularTVShows(pagePopularTVShow)
                .enqueue(initHandleResponse(KEY_GET_POPULAR_TVSHOWS));
    }

    public void getListPopularMovies() {
        pagePopularMovie++;
        Log.i(TAG, "Call API popular: type=" + type + ", page=" + pagePopularMovie);
        getAPI().getPopularMovies(pagePopularMovie)
                .enqueue(initHandleResponse(KEY_GET_POPULAR_MOVIES));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (key.equals(KEY_GET_TRENDING_MOVIES)) {
            MovieRes res = (MovieRes) body;
            addToResulMovieTrendingList(res.resultsMovie);
            super.handleSuccess(key, body);
        } else if (key.equals(KEY_GET_POPULAR_MOVIES)) {
            MovieRes res = (MovieRes) body;
            addToResulMoviePopularList(res.resultsMovie);
            super.handleSuccess(key, body);
        } else if (key.equals(KEY_GET_POPULAR_TVSHOWS)) {
            TVShowRes res = (TVShowRes) body;
            addToResulTVShowList(res.resultsTVShow);
            super.handleSuccess(key, body);
        }
    }

    public void addToResulMovieTrendingList(List<MovieRes.Result> results) {
        resultMovieTrendingList.addAll(results);
    }

    public void addToResulMoviePopularList(List<MovieRes.Result> results) {
        resultMoviePopularList.addAll(results);
    }

    public void addToResulTVShowList(List<TVShowRes.Result> results) {
        resultTVShowList.addAll(results);
    }

    public List<MovieRes.Result> getResultMovieTrendingList() {
        return resultMovieTrendingList;
    }

    public List<MovieRes.Result> getResultMoviePopularList() {
        return resultMoviePopularList;
    }

    public List<TVShowRes.Result> getResultTVShowList() {
        return resultTVShowList;
    }

    public void clearMovieTrendingList() {
        resultMovieTrendingList.clear();
        pageTrendingMovie = 0;
    }

    public void clearMoviePopularList() {
        pagePopularMovie = 0;
        resultMoviePopularList.clear();
    }

    public void clearTVShowList() {
        resultTVShowList.clear();
        pagePopularTVShow = 0;
    }
}
