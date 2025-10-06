package com.duc.themovie.view.fragment;

import static com.duc.themovie.viewmodel.RecVM.KEY_GET_RECOMMENDATION_MOVIES;
import static com.duc.themovie.viewmodel.RecVM.KEY_GET_RECOMMENDATION_TVSHOWS;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.duc.themovie.databinding.RecommendationFragmentBinding;
import com.duc.themovie.view.adapter.ItemAdapter;
import com.duc.themovie.viewmodel.RecVM;

public class RecommnedationFragment extends BaseFrg<RecommendationFragmentBinding, RecVM> {
    ItemAdapter popularSectionAdpt;

    @Override
    protected Class<RecVM> getClassViewModel() {
        return RecVM.class;
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            int itemId = getArguments().getInt("itemId");
            if (getArguments().getString("itemType").equals("movies")) {
                viewModel.getRecMovies(itemId);
                popularSectionAdpt = new ItemAdapter(viewModel.getRecMovieList(), context, true);
            } else {
                viewModel.getRecTVShows(itemId);
                popularSectionAdpt = new ItemAdapter(viewModel.getRecTVSHowList(), context, false);
            }
            binding.rvRecommendation.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            );
            binding.rvRecommendation.setAdapter(popularSectionAdpt);
        }
    }

    @Override
    protected RecommendationFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return RecommendationFragmentBinding.inflate(inflater, container, false);
    }

    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(KEY_GET_RECOMMENDATION_MOVIES)) {
            popularSectionAdpt.updateListResult(viewModel.getRecMovieList());
        } else if (key.equals(KEY_GET_RECOMMENDATION_TVSHOWS)) {
            popularSectionAdpt.updateListResult(viewModel.getRecTVSHowList());
        }
        super.apiSuccess(key, data);
    }
}
