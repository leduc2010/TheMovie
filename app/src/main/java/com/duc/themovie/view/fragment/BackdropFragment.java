package com.duc.themovie.view.fragment;

import static com.duc.themovie.viewmodel.BackdropVM.KEY_GET_BACKDROP_MOVIE;
import static com.duc.themovie.viewmodel.BackdropVM.KEY_GET_BACKDROP_TV;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.duc.themovie.databinding.BackdropFragmentBinding;
import com.duc.themovie.view.adapter.ImageAdapter;
import com.duc.themovie.viewmodel.BackdropVM;

import java.util.Objects;

public class BackdropFragment extends BaseFrg<BackdropFragmentBinding, BackdropVM> {
    private ImageAdapter imgAdpt;

    @Override
    protected Class<BackdropVM> getClassViewModel() {
        return BackdropVM.class;
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
            imgAdpt = new ImageAdapter(viewModel.getBackdropList(), context);
            binding.rvBackdrop.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            );
            binding.rvBackdrop.setAdapter(imgAdpt);
        }
    }

    @Override
    protected BackdropFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return BackdropFragmentBinding.inflate(inflater, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(KEY_GET_BACKDROP_MOVIE) || key.equals(KEY_GET_BACKDROP_TV)) {
            imgAdpt.notifyDataSetChanged();
        }
        super.apiSuccess(key, data);
    }
}
