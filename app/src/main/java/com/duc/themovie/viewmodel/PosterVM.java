package com.duc.themovie.viewmodel;

import com.duc.themovie.api.res.ImageRes;

import java.util.ArrayList;
import java.util.List;

public class PosterVM extends BaseViewModel {
    public static final String KEY_GET_POSTER_MOVIE = "KEY_GET_POSTER_MOVIE";
    public static final String KEY_GET_POSTER_TV = "KEY_GET_POSTER_TV";
    private final List<ImageRes.Poster> posterList = new ArrayList<>();

    public List<ImageRes.Poster> getPosterList() {
        return posterList;
    }

    public void getImageMovie(int itemId) {
        getAPI().getImageMovie(itemId).enqueue(initHandleResponse(KEY_GET_POSTER_MOVIE));
    }
    public void getImageTVShow(int itemId) {
        getAPI().getImageTV(itemId).enqueue(initHandleResponse(KEY_GET_POSTER_TV));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (key.equals(KEY_GET_POSTER_MOVIE) || key.equals(KEY_GET_POSTER_TV)) {
            ImageRes res = (ImageRes) body;
            posterList.clear();
            posterList.addAll(res.posters);
        }
        super.handleSuccess(key, body);
    }

}
