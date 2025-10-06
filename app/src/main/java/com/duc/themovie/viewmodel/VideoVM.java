package com.duc.themovie.viewmodel;

import com.duc.themovie.api.res.VideoRes;

import java.util.ArrayList;
import java.util.List;

public class VideoVM extends BaseViewModel {
    public static final String KEY_GET_VIDEO_MOVIE = "KEY_GET_VIDEO_MOVIE";
    public static final String KEY_GET_VIDEO_TV = "KEY_GET_VIDEO_TV";
    private final List<VideoRes.Result> videoList = new ArrayList<>();

    public List<VideoRes.Result> getVideoList() {
        return videoList;
    }

    public void getVideoMovie(int itemId) {
        getAPI().getVideoMovies(itemId).enqueue(initHandleResponse(KEY_GET_VIDEO_MOVIE));
    }

    public void getVideoTVShow(int itemId) {
        getAPI().getVideoTVs(itemId).enqueue(initHandleResponse(KEY_GET_VIDEO_TV));
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        if (key.equals(KEY_GET_VIDEO_MOVIE) || key.equals(KEY_GET_VIDEO_TV)) {
            VideoRes res = (VideoRes) body;
            videoList.clear();
            videoList.addAll(res.results);
        }
        super.handleSuccess(key, body);
    }
}
