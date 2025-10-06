package com.duc.themovie.viewmodel;

import com.duc.themovie.api.res.ImageRes;

import java.util.ArrayList;
import java.util.List;

public class BackdropVM extends BaseViewModel {
    public static final String KEY_GET_BACKDROP_MOVIE = "KEY_GET_BACKDROP_MOVIE";
    public static final String KEY_GET_BACKDROP_TV = "KEY_GET_BACKDROP_TV";
    private final List<ImageRes.Backdrop> backdropList = new ArrayList<>();

    public List<ImageRes.Backdrop> getBackdropList() {
        return backdropList;
    }

    public void getImageMovie(int itemId) {
        getAPI().getImageMovie(itemId).enqueue(initHandleResponse(KEY_GET_BACKDROP_MOVIE));
    }

    public void getImageTVShow(int itemId) {
        getAPI().getImageTV(itemId).enqueue(initHandleResponse(KEY_GET_BACKDROP_TV));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (key.equals(KEY_GET_BACKDROP_MOVIE) || key.equals(KEY_GET_BACKDROP_TV)) {
            ImageRes res = (ImageRes) body;
            backdropList.clear();
            backdropList.addAll(res.backdrops);
        }
        super.handleSuccess(key, body);
    }
}
