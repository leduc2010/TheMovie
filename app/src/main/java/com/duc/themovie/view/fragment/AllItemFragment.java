package com.duc.themovie.view.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duc.themovie.databinding.AllMovieFragmentBinding;
import com.duc.themovie.view.adapter.ItemAdapter;
import com.duc.themovie.viewmodel.AllItemVM;

import java.util.ArrayList;

public class AllItemFragment extends BaseFrg<AllMovieFragmentBinding, AllItemVM> {

    private ItemAdapter adapter;

    @Override
    protected Class<AllItemVM> getClassViewModel() {
        return AllItemVM.class;
    }

    @Override
    protected void initView() {
        assert getArguments() != null;
        String sectionType = getArguments().getString("sectionType");
        assert sectionType != null;

        if (sectionType.contains("TV")) {
            adapter = new ItemAdapter(new ArrayList<>(), getContext(), false);
        } else {
            adapter = new ItemAdapter(new ArrayList<>(), getContext(), true);
        }
        binding.rvAllItem.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvAllItem.setAdapter(adapter);

        viewModel.loadByType(sectionType);

        if (sectionType.contains("TV")) {
            viewModel.getTVShows().observe(getViewLifecycleOwner(), results -> adapter.updateData(results));
        } else {
            viewModel.getMovies().observe(getViewLifecycleOwner(), results -> adapter.updateData(results));
        }
        binding.rvAllItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.nextPage();
                }
            }
        });

        binding.btBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack();
        });
    }

    @Override
    protected AllMovieFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return AllMovieFragmentBinding.inflate(inflater, container, false);
    }
}
