package com.duc.themovie.viewmodel;

import com.duc.themovie.api.res.CreditRes;
import com.duc.themovie.api.res.KeywordsMovieRes;
import com.duc.themovie.api.res.KeywordsTVRes;

import java.util.ArrayList;
import java.util.List;

public class DetailItemVM extends BaseViewModel {
    public static final String KEY_GET_DETAIL_MOVIE = "KEY_GET_DETAIL_MOVIE";
    public static final String KEY_GET_DETAIL_TV_SHOW = "KEY_GET_DETAIL_TV_SHOW";
    public static final String KEY_GET_CREDIT_MOVIE = "KEY_GET_CREDIT_MOVIE";
    public static final String KEY_GET_MOVIE_KEYWORDS = "KEY_GET_MOVIE_KEYWORDS";
    public static final String KEY_GET_TVSHOW_CREDITS = "KEY_GET_TVSHOW_CREDITS";
    public static final String KEY_GET_TVSHOW_KEYWORDS = "KEY_GET_TVSHOW_KEYWORDS";
    private final List<CreditRes.Cast> castList = new ArrayList<>();
    private final List<KeywordsMovieRes.Keyword> keywordMovieList = new ArrayList<>();
    private final List<KeywordsTVRes.Keyword> keywordTVList = new ArrayList<>();

    public List<CreditRes.Cast> getCastList() {
        return castList;
    }

    public void getMovieDetail(int itemId) {
        getAPI().getDetailMovie(itemId).enqueue(initHandleResponse(KEY_GET_DETAIL_MOVIE));
    }

    public void getMovieCredits(int itemId) {
        getAPI().getMovieCredits(itemId).enqueue(initHandleResponse(KEY_GET_CREDIT_MOVIE));
    }

    public void getTVShowCredits(int itemId) {
        getAPI().getTVShowCredits(itemId).enqueue(initHandleResponse(KEY_GET_TVSHOW_CREDITS));
    }

    public void getMovieKeywords(int itemId) {
        getAPI().getMovieKeywords(itemId).enqueue(initHandleResponse(KEY_GET_MOVIE_KEYWORDS));
    }

    public void getTVShowKeywords(int itemId) {
        getAPI().getTVShowKeywords(itemId).enqueue(initHandleResponse(KEY_GET_TVSHOW_KEYWORDS));
    }

    public void getTVDetail(int itemId) {
        getAPI().getDetailTVShow(itemId).enqueue(initHandleResponse(KEY_GET_DETAIL_TV_SHOW));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        switch (key) {
            case KEY_GET_CREDIT_MOVIE:
            case KEY_GET_TVSHOW_CREDITS: {
                CreditRes res = (CreditRes) body;
                castList.clear();
                castList.addAll(res.Casts);
                break;
            }
            case KEY_GET_MOVIE_KEYWORDS: {
                KeywordsMovieRes res = (KeywordsMovieRes) body;
                keywordMovieList.clear();
                keywordMovieList.addAll(res.keywords);
                break;
            }
            case KEY_GET_TVSHOW_KEYWORDS: {
                KeywordsTVRes res = (KeywordsTVRes) body;
                keywordTVList.clear();
                keywordTVList.addAll(res.results);
                break;
            }
        }
        super.handleSuccess(key, body);
    }
}
