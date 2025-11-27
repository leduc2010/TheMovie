package com.duc.themovie.viewmodel;

import android.util.Log;

import com.duc.themovie.api.res.TVShowRes;
import com.duc.themovie.model.Section;
import com.duc.themovie.view.SectionVM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TVVM extends BaseViewModel implements SectionVM {
    private static final String TAG = MovieVM.class.getName();
    public static final String KEY_GET_TRENDING_TVSHOWS = "KEY_GET_TRENDING_TVSHOWS";
    public static final String KEY_GET_POPULAR_TVSHOWS = "KEY_GET_POPULAR_TVSHOWS";
    public static final String KEY_GET_UPCOMING_TVSHOWS = "KEY_GET_UPCOMING_TVSHOWS";
    public static final String KEY_GET_TOP_RATED_TVSHOWS = "KEY_GET_TOP_RATED_TVSHOWS";
    static final String KEY_GET_AIRING_TODAY_TVSHOWS = "KEY_GET_AIRING_TODAY_TVSHOWS";
    static final String KEY_GET_ON_THE_AIR_TVSHOWS = "KEY_GET_ON_THE_AIR_TVSHOWS";

    private Map<String, Integer> pageMap = new HashMap<String, Integer>() {{
        put(KEY_GET_TRENDING_TVSHOWS, 0);
        put(KEY_GET_POPULAR_TVSHOWS, 0);
        put(KEY_GET_ON_THE_AIR_TVSHOWS, 0);
        put(KEY_GET_AIRING_TODAY_TVSHOWS, 0);
        put(KEY_GET_TOP_RATED_TVSHOWS, 0);
    }};
    private Map<String, List<TVShowRes.Result>> tvShowMap = new HashMap<String, List<TVShowRes.Result>>() {{
        put(KEY_GET_TRENDING_TVSHOWS, new ArrayList<>());
        put(KEY_GET_POPULAR_TVSHOWS, new ArrayList<>());
        put(KEY_GET_ON_THE_AIR_TVSHOWS, new ArrayList<>());
        put(KEY_GET_AIRING_TODAY_TVSHOWS, new ArrayList<>());
        put(KEY_GET_TOP_RATED_TVSHOWS, new ArrayList<>());
    }};

    private String timeWindow = "day";
    private ArrayList<Section> sections = new ArrayList<>();

    public ArrayList<Section> getSections() {
        return sections;
    }

    public List<TVShowRes.Result> getResultList(String key) {
        return tvShowMap.getOrDefault(key, new ArrayList<>());
    }

    public void getListTrending() {
        int page = nextPage(KEY_GET_TRENDING_TVSHOWS);
        Log.i(TAG, "Call API trending: timeWindow=" + timeWindow + ", page=" + page);
        getAPI().getTrendingTVShows(timeWindow, page).enqueue(initHandleResponse(KEY_GET_TRENDING_TVSHOWS));
    }


    public void getListPopular() {
        int page = nextPage(KEY_GET_POPULAR_TVSHOWS);
        Log.i(TAG, "Call API popular: page=" + page);
        getAPI().getPopularTVShows(page).enqueue(initHandleResponse(KEY_GET_POPULAR_TVSHOWS));
    }

    @Override
    public void getListAiringToday() {
        int page = nextPage(KEY_GET_AIRING_TODAY_TVSHOWS);
        Log.i(TAG, "Call API airing today tv shows: page=" + page);
        getAPI().getAiringTodayTVShows(page).enqueue(initHandleResponse(KEY_GET_AIRING_TODAY_TVSHOWS));
    }

    @Override
    public void getListOnTheAir() {
        int page = nextPage(KEY_GET_ON_THE_AIR_TVSHOWS);
        Log.i(TAG, "Call API on the air tv shows: page=" + page);
        getAPI().getOnTheAirTVShows(page).enqueue(initHandleResponse(KEY_GET_ON_THE_AIR_TVSHOWS));
    }

    @Override
    public void getListTopRated() {
        int page = nextPage(KEY_GET_TOP_RATED_TVSHOWS);
        Log.i(TAG, "Call API top rated tv shows: page=" + page);
        getAPI().getTopRatedTVShows(page).enqueue(initHandleResponse(KEY_GET_TOP_RATED_TVSHOWS));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        switch (key) {
            case KEY_GET_TRENDING_TVSHOWS: {
                TVShowRes res = (TVShowRes) body;
                addToTVShowMap(KEY_GET_TRENDING_TVSHOWS, res.resultsTVShow);
                addSection(new Section("Trending TV Shows", null, res.resultsTVShow, "TrendingTVShows"));
                super.handleSuccess(key, body);
                break;
            }
            case KEY_GET_POPULAR_TVSHOWS: {
                TVShowRes res = (TVShowRes) body;
                addToTVShowMap(KEY_GET_POPULAR_TVSHOWS, res.resultsTVShow);
                addSection(new Section("Popular TV Shows", null, res.resultsTVShow, "PopularTVShows"));
                super.handleSuccess(key, body);
                break;
            }
            case KEY_GET_AIRING_TODAY_TVSHOWS: {
                TVShowRes res = (TVShowRes) body;
                addToTVShowMap(KEY_GET_AIRING_TODAY_TVSHOWS, res.resultsTVShow);
                addSection(new Section("Airing Today TV shows", null, res.resultsTVShow, "AiringTodayTVShows"));
                super.handleSuccess(key, body);
                break;
            }
            case KEY_GET_ON_THE_AIR_TVSHOWS: {
                TVShowRes res = (TVShowRes) body;
                addToTVShowMap(KEY_GET_ON_THE_AIR_TVSHOWS, res.resultsTVShow);
                addSection(new Section("On The air TV shows", null, res.resultsTVShow, "OnTheAirTVShows"));
                super.handleSuccess(key, body);
                break;
            }
            case KEY_GET_TOP_RATED_TVSHOWS: {
                TVShowRes res = (TVShowRes) body;
                addToTVShowMap(KEY_GET_TOP_RATED_TVSHOWS, res.resultsTVShow);
                addSection(new Section("Top Rated TV shows", null, res.resultsTVShow, "TopRatedTVShows"));
                super.handleSuccess(key, body);
                break;
            }
        }
    }

    private void addSection(Section section) {
        for (Section s : sections) {
            if (s.getTitle().equals(section.getTitle())) {
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
            case "TrendingTVShows":
                return 0;
            case "PopularTVShows":
                return 1;
            case "AiringTodayTVShows":
                return 2;
            case "OnTheAirTVShows":
                return 3;
            case "TopRatedTVShows":
                return 4;
            default:
                return 999;
        }
    }

    public void addToTVShowMap(String key, List<TVShowRes.Result> results) {
        List<TVShowRes.Result> list = tvShowMap.computeIfAbsent(key, k -> new ArrayList<>());
        list.addAll(results);
    }

    @Override
    public void clearResultList(String key) {
        List<TVShowRes.Result> list = tvShowMap.get(key);
        if (list != null) {
            list.clear();
        }
    }

    @Override
    public String getKeyBySectionType(String sectionType) {
        switch (sectionType) {
            case "PopularTVShows":
                return KEY_GET_POPULAR_TVSHOWS;
            case "AiringTodayTVShows":
                return KEY_GET_AIRING_TODAY_TVSHOWS;
            case "OnTheAirTVShows":
                return KEY_GET_ON_THE_AIR_TVSHOWS;
            case "TopRatedTVShows":
                return KEY_GET_TOP_RATED_TVSHOWS;
            case "TrendingTVShows":
                return KEY_GET_TRENDING_TVSHOWS;
        }
        return "";
    }

    @Override
    public String getTimeWindow() {
        return this.timeWindow;
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
