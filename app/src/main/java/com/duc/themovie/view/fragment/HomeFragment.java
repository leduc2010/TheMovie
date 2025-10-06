package com.duc.themovie.view.fragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.duc.themovie.R;
import com.duc.themovie.databinding.HomeFragmentBinding;
import com.duc.themovie.view.adapter.ItemAdapter;
import com.duc.themovie.viewmodel.HomeVM;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends BaseFrg<HomeFragmentBinding, HomeVM> {
    public static final String TAG = HomeFragment.class.getName();

    private ItemAdapter trendingAdpt;
    private ItemAdapter popularAdpt;


    @Override
    protected Class<HomeVM> getClassViewModel() {
        return HomeVM.class;
    }

    @Override
    protected void initView() {

        viewModel.getListTrendingMovies();
        viewModel.getListPopularMovies();

        trendingAdpt = new ItemAdapter<>(viewModel.getResultMovieTrendingList(), context, true);
        popularAdpt = new ItemAdapter<>(viewModel.getResultMoviePopularList(), context, true);

        binding.includedTrending.rvTrendingMovies.setAdapter(trendingAdpt);
        binding.includePopular.rvPopularMovies.setAdapter(popularAdpt);
        initToggle();
        initTabLayout();
    }

    private void initTabLayout() {
        binding.includePopular.tabLayout.addTab(binding.includePopular.tabLayout.newTab().setText("Movies"));
        binding.includePopular.tabLayout.addTab(binding.includePopular.tabLayout.newTab().setText("TV Shows"));
        binding.includePopular.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    viewModel.clearMoviePopularList();
                    popularAdpt.setMovie(true);
                    viewModel.getListPopularMovies();
                    popularAdpt.updateListResult(viewModel.getResultMoviePopularList());
                } else {
                    viewModel.clearTVShowList();
                    popularAdpt.setMovie(false);
                    viewModel.getListPopularTVShows();
                    popularAdpt.updateListResult(viewModel.getResultTVShowList());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initToggle() {
        binding.includedTrending.rvTrendingMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.getListTrendingMovies();
                }
            }
        });

        // default chọn Day
        binding.includedTrending.toggleGroup.check(R.id.btnDay);
        setButtonSelected(binding.includedTrending.btnDay, true);
        setButtonSelected(binding.includedTrending.btnWeek, false);

        binding.includedTrending.toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btnDay) {
                    handleToggleChanged("day", binding.includedTrending.btnDay, binding.includedTrending.btnWeek);
                } else if (checkedId == R.id.btnWeek) {
                    handleToggleChanged("week", binding.includedTrending.btnWeek, binding.includedTrending.btnDay);
                }
            }
        });
    }

    private void handleToggleChanged(String timeWindow, MaterialButton selectedBtn, MaterialButton unselectedBtn) {
        setButtonSelected(selectedBtn, true);
        setButtonSelected(unselectedBtn, false);

        viewModel.clearMovieTrendingList();
        viewModel.setTimeWindow(timeWindow);

        // cập nhật adapter rỗng trước để reset UI
        if (trendingAdpt != null) {
            trendingAdpt.updateListResult(viewModel.getResultMovieTrendingList());
        }
        binding.includedTrending.rvTrendingMovies.smoothScrollToPosition(0);
        viewModel.getListTrendingMovies();
    }

    private void setButtonSelected(MaterialButton btn, boolean selected) {
        if (selected) {
            btn.setIconTintResource(R.color.cyan);
            btn.setIcon(ContextCompat.getDrawable(requireContext(), R.mipmap.ic_check));
            btn.setIconTintResource(android.R.color.white);
        } else {
            btn.setIconTintResource(android.R.color.black);
            btn.setIcon(null);
        }
    }

    @Override
    protected HomeFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return HomeFragmentBinding.inflate(inflater, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(HomeVM.KEY_GET_TRENDING_MOVIES)) {
            trendingAdpt.updateListResult(viewModel.getResultMovieTrendingList());
        }
        if (key.equals(HomeVM.KEY_GET_POPULAR_MOVIES)) {
            popularAdpt.updateListResult(viewModel.getResultMoviePopularList());
        } else if (key.equals(HomeVM.KEY_GET_POPULAR_TVSHOWS)) {
            popularAdpt.updateListResult(viewModel.getResultTVShowList());
        }
    }
}
