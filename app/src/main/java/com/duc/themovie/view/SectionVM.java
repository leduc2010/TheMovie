package com.duc.themovie.view;

import com.duc.themovie.api.res.MovieRes;

import java.util.List;

public interface SectionVM {
    void getListTrending();

    void getListPopular();

    default void getListNowPlaying() {
    }

    default void getListUpcoming() {
    }

    default void getListAiringToday() {
    }

    default void getListOnTheAir() {
    }

    void getListTopRated();

    <T> List<T> getResultList(String key);

    public void resetPage(String key);

    public void setTimeWindow(String timeWindow);

    public void clearResultList(String key);
    String getKeyBySectionType(String sectionType);
}
