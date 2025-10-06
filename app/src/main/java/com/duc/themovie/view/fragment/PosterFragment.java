package com.duc.themovie.view.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.duc.themovie.databinding.PosterFragmentBinding;
import com.duc.themovie.view.adapter.ImageAdapter;
import com.duc.themovie.viewmodel.PosterVM;

import java.util.Objects;

public class PosterFragment extends BaseFrg<PosterFragmentBinding, PosterVM> {
    private ImageAdapter imgAdpt;

    @Override
    protected Class<PosterVM> getClassViewModel() {
        return PosterVM.class;
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            int itemId = getArguments().getInt("itemId");
            if (Objects.equals(getArguments().getString("itemType"), "movies")) {
                viewModel.getImageMovie(itemId);
            } else {
                viewModel.getImageTVShow(itemId);
            }
            imgAdpt = new ImageAdapter(viewModel.getPosterList(), context);
            binding.rvPoster.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            binding.rvPoster.setAdapter(imgAdpt);
        }
    }

    @Override
    protected PosterFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return PosterFragmentBinding.inflate(inflater, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(PosterVM.KEY_GET_POSTER_MOVIE) || key.equals(PosterVM.KEY_GET_POSTER_TV)) {
            imgAdpt.notifyDataSetChanged();
        }
        super.apiSuccess(key, data);
    }
}
