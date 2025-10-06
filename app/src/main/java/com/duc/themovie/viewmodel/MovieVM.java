package com.duc.themovie.viewmodel;


import android.util.Log;

import com.duc.themovie.api.res.MovieRes;
import com.duc.themovie.model.Section;
import com.duc.themovie.view.SectionVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieVM extends BaseViewModel implements SectionVM {
    private static final String TAG = MovieVM.class.getName();
    public static final String KEY_GET_TRENDING_MOVIES = "KEY_GET_TRENDING_MOVIES";
    public static final String KEY_GET_POPULAR_MOVIES = "KEY_GET_POPULAR_MOVIES";
    public static final String KEY_GET_NOW_PLAYING_MOVIES = "KEY_GET_NOW_PLAYING_MOVIES";
    public static final String KEY_GET_UPCOMING_MOVIES = "KEY_GET_UPCOMING_MOVIES";
    public static final String KEY_GET_TOP_RATED_MOVIES = "KEY_GET_TOP_RATED_MOVIES";

    private Map<String, Integer> pageMap = new HashMap<String, Integer>() {{
        put(KEY_GET_TRENDING_MOVIES, 0);
        put(KEY_GET_POPULAR_MOVIES, 0);
        put(KEY_GET_NOW_PLAYING_MOVIES, 0);
        put(KEY_GET_UPCOMING_MOVIES, 0);
        put(KEY_GET_TOP_RATED_MOVIES, 0);
    }};
    private Map<String, List<MovieRes.Result>> movieMap = new HashMap<String, List<MovieRes.Result>>() {{
        put(KEY_GET_TRENDING_MOVIES, new ArrayList<>());
        put(KEY_GET_POPULAR_MOVIES, new ArrayList<>());
    }};

    private String timeWindow = "day";
    private ArrayList<Section> sections = new ArrayList<>();

    public ArrayList<Section> getSections() {
        return sections;
    }

    public List<MovieRes.Result> getResultList(String key) {
        return movieMap.getOrDefault(key, new ArrayList<>());
    }

    public void getListTrending() {
        int page = nextPage(KEY_GET_TRENDING_MOVIES);
        Log.i(TAG, "Call API trending: timeWindow=" + timeWindow + ", page=" + page);
        getAPI().getTrendingMovies(timeWindow, page).enqueue(initHandleResponse(KEY_GET_TRENDING_MOVIES));
    }


    public void getListPopular() {
        int page = nextPage(KEY_GET_POPULAR_MOVIES);
        Log.i(TAG, "Call API popular: page=" + page);
        getAPI().getPopularMovies(page).enqueue(initHandleResponse(KEY_GET_POPULAR_MOVIES));
    }

    public void getListNowPlaying() {
        int page = nextPage(KEY_GET_NOW_PLAYING_MOVIES);
        Log.i(TAG, "Call API now playing: page=" + page);
        getAPI().getNowPlayingMovies(page).enqueue(initHandleResponse(KEY_GET_NOW_PLAYING_MOVIES));
    }

    public void getListUpcoming() {
        int page = nextPage(KEY_GET_UPCOMING_MOVIES);
        Log.i(TAG, "Call API upcoming: page=" + page);
        getAPI().getUpcomingMovies(page).enqueue(initHandleResponse(KEY_GET_UPCOMING_MOVIES));
    }

    public void getListTopRated() {
        int page = nextPage(KEY_GET_TOP_RATED_MOVIES);
        Log.i(TAG, "Call API top rated: page=" + page);
        getAPI().getTopRatedMovies(page).enqueue(initHandleResponse(KEY_GET_TOP_RATED_MOVIES
        ));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (key.equals(KEY_GET_TRENDING_MOVIES)) {
            MovieRes res = (MovieRes) body;
            addToMovieMap(KEY_GET_TRENDING_MOVIES, res.resultsMovie);
            addSection(new Section("Trending Movies", res.resultsMovie, null, "TrendingMovie"));
            super.handleSuccess(key, body);
        } else if (key.equals(KEY_GET_POPULAR_MOVIES)) {
            MovieRes res = (MovieRes) body;
            addToMovieMap(KEY_GET_POPULAR_MOVIES, res.resultsMovie);
            addSection(new Section("Popular Movies", res.resultsMovie, null, "PopularMovie"));
            super.handleSuccess(key, body);
        } else if (key.equals(KEY_GET_NOW_PLAYING_MOVIES)) {
            MovieRes res = (MovieRes) body;
            addToMovieMap(KEY_GET_NOW_PLAYING_MOVIES, res.resultsMovie);
            addSection(new Section("Now playing Movies", res.resultsMovie, null, "NowPlayingMovies"));
            super.handleSuccess(key, body);
        } else if (key.equals(KEY_GET_UPCOMING_MOVIES)) {
            MovieRes res = (MovieRes) body;
            addToMovieMap(KEY_GET_UPCOMING_MOVIES, res.resultsMovie);
            addSection(new Section("Upcoming Movies", res.resultsMovie, null, "UpcomingMovies"));
            super.handleSuccess(key, body);
        } else if (key.equals(KEY_GET_TOP_RATED_MOVIES)) {
            MovieRes res = (MovieRes) body;
            addToMovieMap(KEY_GET_TOP_RATED_MOVIES, res.resultsMovie);
            addSection(new Section("Top rated Movies", res.resultsMovie, null, "TopRatedMovies"));
            super.handleSuccess(key, body);
        }
    }

    private void addSection(Section section) {
        for (Section s : sections) {
            if (s.getType().equals(section.getType())) {
                return;
            }
        }
        sections.add(section);

        sections.sort((s1, s2) -> {
            int o1 = getOrder(s1.getType());
            int o2 = getOrder(s2.getType());
            return Integer.compare(o1, o2);
        });
    }

    private int getOrder(String type) {
        switch (type) {
            case "TrendingMovie": return 0;
            case "PopularMovie": return 1;
            case "NowPlayingMovies": return 2;
            case "UpcomingMovies": return 3;
            default: return 999;
        }
    }

    public void addToMovieMap(String key, List<MovieRes.Result> results) {
        List<MovieRes.Result> list = movieMap.computeIfAbsent(key, k -> new ArrayList<>());
        list.addAll(results);
    }

    public void clearResultList(String key) {
        List<MovieRes.Result> list = movieMap.get(key);
        if (list != null) {
            list.clear();
        }
    }

    @Override
    public String getKeyBySectionType(String sectionType) {
        switch (sectionType) {
            case "PopularMovie": return KEY_GET_POPULAR_MOVIES;
            case "NowPlayingMovies": return KEY_GET_NOW_PLAYING_MOVIES;
            case "UpcomingMovies": return KEY_GET_UPCOMING_MOVIES;
            case "TopRatedMovies": return KEY_GET_TOP_RATED_MOVIES;
            case "TrendingMovie": return KEY_GET_TRENDING_MOVIES;
        }
        return "";
    }

    private int nextPage(String key) {
        int p = pageMap.getOrDefault(key, 0) + 1;
        pageMap.put(key, p);
        return p;
    }

    public void resetPage(String key) {
        pageMap.put(key, 0);
    }

    public void setTimeWindow(String timeWindow) {
        this.timeWindow = timeWindow;
    }
}
