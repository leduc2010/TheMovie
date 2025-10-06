package com.duc.themovie.view.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.duc.themovie.databinding.TvFragmentBinding;
import com.duc.themovie.view.adapter.SectionAdapter;
import com.duc.themovie.viewmodel.TVVM;

public class TVFragment extends BaseFrg<TvFragmentBinding, TVVM> {

    private static final String KEY_GET_TRENDING_TVSHOWS = "KEY_GET_TRENDING_TVSHOWS";
    private static final String KEY_GET_POPULAR_TVSHOWS = "KEY_GET_POPULAR_TVSHOWS";
        private static final String KEY_GET_AIRING_TODAY_TVSHOWS = "KEY_GET_AIRING_TODAY_TVSHOWS";
    private static final String KEY_GET_ON_THE_AIR_TVSHOWS = "KEY_GET_ON_THE_AIR_TVSHOWS";
    private static final String KEY_GET_TOP_RATED_TVSHOWS = "KEY_GET_TOP_RATED_TVSHOWS";

    @Override
    protected Class<TVVM> getClassViewModel() {
        return TVVM.class;
    }

    @Override
    protected TvFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return TvFragmentBinding.inflate(inflater, container, false);
    }

    private SectionAdapter sectionAdapter;
    public static final String TAG = MovieFragment.class.getName();


    @Override
    protected void initView() {
        viewModel.getListTrending();
        viewModel.getListPopular();
        viewModel.getListAiringToday();
        viewModel.getListOnTheAir();
        viewModel.getListTopRated();
        sectionAdapter = new SectionAdapter(context, viewModel.getSections(), viewModel);
        binding.rvSections.setAdapter(sectionAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(KEY_GET_TRENDING_TVSHOWS)) {
            sectionAdapter.updateListTrendingMovies(viewModel.getResultList(KEY_GET_TRENDING_TVSHOWS));
        } else if (key.equals(KEY_GET_POPULAR_TVSHOWS)) {
            sectionAdapter.notifyDataSetChanged();
        } else if (key.equals(KEY_GET_AIRING_TODAY_TVSHOWS)) {
            sectionAdapter.notifyDataSetChanged();
        } else if (key.equals(KEY_GET_ON_THE_AIR_TVSHOWS)) {
            sectionAdapter.notifyDataSetChanged();
        } else if (key.equals(KEY_GET_TOP_RATED_TVSHOWS)) {
            sectionAdapter.notifyDataSetChanged();
        }
        super.apiSuccess(key, data);
    }
}
