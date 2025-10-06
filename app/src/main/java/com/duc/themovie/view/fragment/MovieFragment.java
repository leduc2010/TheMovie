package com.duc.themovie.view.fragment;

import static com.duc.themovie.viewmodel.HomeVM.KEY_GET_POPULAR_MOVIES;
import static com.duc.themovie.viewmodel.HomeVM.KEY_GET_TRENDING_MOVIES;
import static com.duc.themovie.viewmodel.MovieVM.KEY_GET_NOW_PLAYING_MOVIES;
import static com.duc.themovie.viewmodel.MovieVM.KEY_GET_TOP_RATED_MOVIES;
import static com.duc.themovie.viewmodel.MovieVM.KEY_GET_UPCOMING_MOVIES;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.duc.themovie.databinding.MovieFragmentBinding;
import com.duc.themovie.view.adapter.SectionAdapter;
import com.duc.themovie.viewmodel.MovieVM;

public class MovieFragment extends BaseFrg<MovieFragmentBinding, MovieVM> {

    private SectionAdapter sectionAdapter;
    public static final String TAG = MovieFragment.class.getName();

    @Override
    protected Class<MovieVM> getClassViewModel() {
        return MovieVM.class;
    }

    @Override
    protected void initView() {
        viewModel.getListPopular();
        viewModel.getListTrending();
        viewModel.getListNowPlaying();
        viewModel.getListUpcoming();
        viewModel.getListTopRated();

        sectionAdapter = new SectionAdapter(context, viewModel.getSections(), viewModel);
        binding.rvSections.setAdapter(sectionAdapter);
    }

    @Override
    protected MovieFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return MovieFragmentBinding.inflate(inflater, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(KEY_GET_TRENDING_MOVIES)) {
            sectionAdapter.updateListTrendingMovies(viewModel.getResultList(KEY_GET_TRENDING_MOVIES));
        } else if (key.equals(KEY_GET_POPULAR_MOVIES)) {
            sectionAdapter.notifyDataSetChanged();
        } else if (key.equals(KEY_GET_NOW_PLAYING_MOVIES)) {
            sectionAdapter.notifyDataSetChanged();
        } else if (key.equals(KEY_GET_UPCOMING_MOVIES)) {
            sectionAdapter.notifyDataSetChanged();
        } else if (key.equals(KEY_GET_TOP_RATED_MOVIES)) {
            sectionAdapter.notifyDataSetChanged();
        }
        super.apiSuccess(key, data);
    }
}
