package com.duc.themovie.view.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.duc.themovie.view.fragment.BackdropFragment;
import com.duc.themovie.view.fragment.PosterFragment;
import com.duc.themovie.view.fragment.RecommnedationFragment;
import com.duc.themovie.view.fragment.SimilarMoviesFragment;
import com.duc.themovie.view.fragment.VideoFragment;

public class RecommnedationAdapter extends FragmentStateAdapter {
    private int itemId;
    private String itemType;


    public RecommnedationAdapter(@NonNull FragmentActivity fragmentActivity, int itemId, String itemType) {
        super(fragmentActivity);
        this.itemId = itemId;
        this.itemType = itemType;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return createFragmentWithArgs(new RecommnedationFragment());
            case 1:
                return createFragmentWithArgs(new SimilarMoviesFragment());
            default:
                return new RecommnedationFragment();
        }
    }

    private Fragment createFragmentWithArgs(Fragment fragment) {
        Bundle args = new Bundle();
        args.putInt("itemId", itemId);
        args.putString("itemType", itemType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
