package com.duc.themovie.viewmodel;

import static com.duc.themovie.viewmodel.HomeVM.KEY_GET_POPULAR_TVSHOWS;
import static com.duc.themovie.viewmodel.MovieVM.KEY_GET_NOW_PLAYING_MOVIES;
import static com.duc.themovie.viewmodel.MovieVM.KEY_GET_POPULAR_MOVIES;
import static com.duc.themovie.viewmodel.MovieVM.KEY_GET_TOP_RATED_MOVIES;
import static com.duc.themovie.viewmodel.MovieVM.KEY_GET_UPCOMING_MOVIES;
import static com.duc.themovie.viewmodel.TVVM.KEY_GET_AIRING_TODAY_TVSHOWS;
import static com.duc.themovie.viewmodel.TVVM.KEY_GET_ON_THE_AIR_TVSHOWS;
import static com.duc.themovie.viewmodel.TVVM.KEY_GET_TOP_RATED_TVSHOWS;

import androidx.lifecycle.MutableLiveData;

import com.duc.themovie.api.res.MovieRes;
import com.duc.themovie.api.res.TVShowRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllItemVM extends BaseViewModel {
    private MutableLiveData<List<MovieRes.Result>> movies = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<TVShowRes.Result>> tvShows = new MutableLiveData<>(new ArrayList<>());

    private Map<String, Integer> pageMap = new HashMap<>();
    private String currentType = "";

    public MutableLiveData<List<MovieRes.Result>> getMovies() {
        return movies;
    }

    public MutableLiveData<List<TVShowRes.Result>> getTVShows() {
        return tvShows;
    }

    public void loadByType(String sectionType) {
        this.currentType = sectionType;
        pageMap.put(sectionType, 1);
        getListMovies(sectionType, 1);
    }

    public void getListMovies(String sectionType, int page) {

        switch (sectionType) {
            case "PopularMovie":
                getAPI().getPopularMovies(page).enqueue(initHandleResponse(KEY_GET_POPULAR_MOVIES));
                break;
            case "NowPlayingMovies":
                getAPI().getNowPlayingMovies(page).enqueue(initHandleResponse(KEY_GET_NOW_PLAYING_MOVIES));
                break;
            case "UpcomingMovies":
                getAPI().getUpcomingMovies(page).enqueue(initHandleResponse(KEY_GET_UPCOMING_MOVIES));
                break;
            case "TopRatedMovies":
                getAPI().getTopRatedMovies(page).enqueue(initHandleResponse(KEY_GET_TOP_RATED_MOVIES));
                break;
            case "PopularTVShows":
                getAPI().getPopularTVShows(page).enqueue(initHandleResponse(KEY_GET_POPULAR_TVSHOWS));
                break;
            case "AiringTodayTVShows":
                getAPI().getAiringTodayTVShows(page).enqueue(initHandleResponse(KEY_GET_AIRING_TODAY_TVSHOWS));
                break;
            case "OnTheAirTVShows":
                getAPI().getOnTheAirTVShows(page).enqueue(initHandleResponse(KEY_GET_ON_THE_AIR_TVSHOWS));
                break;
            case "TopRatedTVShows":
                getAPI().getTopRatedTVShows(page).enqueue(initHandleResponse(KEY_GET_TOP_RATED_TVSHOWS));
                break;
        }
    }

    public void nextPage() {
        int nextPage = pageMap.get(currentType) + 1;
        pageMap.put(currentType, nextPage);
        getListMovies(currentType, nextPage);
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (body instanceof MovieRes) {
            MovieRes res = (MovieRes) body;
            addToResulMovieTrendingList(res.resultsMovie);
            super.handleSuccess(key, body);
        } else if (body instanceof TVShowRes) {
            TVShowRes res = (TVShowRes) body;
            addTVShows(res.resultsTVShow);
        }
    }

    private void addTVShows(List<TVShowRes.Result> results) {
        List<TVShowRes.Result> currentList = tvShows.getValue();
        if (currentList == null) currentList = new ArrayList<>();
        currentList.addAll(results);
        tvShows.setValue(currentList);
    }

    public void addToResulMovieTrendingList(List<MovieRes.Result> results) {
        List<MovieRes.Result> currentList = movies.getValue();
        if (currentList == null) currentList = new ArrayList<>();
        currentList.addAll(results);
        movies.setValue(currentList);
    }
}
