package com.duc.themovie.view.fragment;

import static com.duc.themovie.viewmodel.SimilarMoviesVM.KEY_GET_SIMILAR_MOVIES;
import static com.duc.themovie.viewmodel.SimilarMoviesVM.KEY_GET_SIMILAR_TVSHOWS;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.duc.themovie.databinding.SimilarMovieFragmentBinding;
import com.duc.themovie.view.adapter.ItemAdapter;
import com.duc.themovie.viewmodel.SimilarMoviesVM;

public class SimilarMoviesFragment extends BaseFrg<SimilarMovieFragmentBinding, SimilarMoviesVM> {
    private ItemAdapter popularSectionAdpt;

    @Override
    protected Class<SimilarMoviesVM> getClassViewModel() {
        return SimilarMoviesVM.class;
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            int itemId = getArguments().getInt("itemId");
            if (getArguments().getString("itemType").equals("movies")) {
                viewModel.getSimilarMovies(itemId);
                popularSectionAdpt = new ItemAdapter(viewModel.getSimilarMovieList(), context, true);
            } else {
                viewModel.getSimilarTVShows(itemId);
                popularSectionAdpt = new ItemAdapter(viewModel.getSimilarTVShowList(), context, false);
            }
            binding.rvSimilarMovie.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            );
            binding.rvSimilarMovie.setAdapter(popularSectionAdpt);
        }
    }

    @Override
    protected SimilarMovieFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return SimilarMovieFragmentBinding.inflate(inflater, container, false);
    }

    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(KEY_GET_SIMILAR_MOVIES)) {
            popularSectionAdpt.updateListResult(viewModel.getSimilarMovieList());
        } else if (key.equals(KEY_GET_SIMILAR_TVSHOWS)) {
            popularSectionAdpt.updateListResult(viewModel.getSimilarTVShowList());
        }
        super.apiSuccess(key, data);
    }
}
