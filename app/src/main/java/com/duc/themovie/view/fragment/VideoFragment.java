package com.duc.themovie.view.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.duc.themovie.databinding.VideosFragmentBinding;
import com.duc.themovie.view.adapter.VideoAdapter;
import com.duc.themovie.viewmodel.VideoVM;

import java.util.Objects;

public class VideoFragment extends BaseFrg<VideosFragmentBinding, VideoVM> {
    private VideoAdapter vidAdpt;

    @Override
    protected Class<VideoVM> getClassViewModel() {
        return VideoVM.class;
    }

    @Override
    protected void initView() {
        if (getArguments() != null) {
            int itemId = getArguments().getInt("itemId");
            if (Objects.equals(getArguments().getString("itemType"), "movies")) {
                viewModel.getVideoMovie(itemId);
            } else {
                viewModel.getVideoTVShow(itemId);
            }
            vidAdpt = new VideoAdapter(viewModel.getVideoList(), context);
            binding.rvVideo.setLayoutManager(
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            );
            binding.rvVideo.setAdapter(vidAdpt);
        }
    }

    @Override
    protected VideosFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return VideosFragmentBinding.inflate(inflater, container, false);
    }

    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(VideoVM.KEY_GET_VIDEO_MOVIE) || key.equals(VideoVM.KEY_GET_VIDEO_TV)) {
            vidAdpt.notifyDataSetChanged();
        }
        super.apiSuccess(key, data);
    }
}


