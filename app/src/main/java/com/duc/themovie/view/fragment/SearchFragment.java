package com.duc.themovie.view.fragment;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.duc.themovie.databinding.SearchFragmentBinding;
import com.duc.themovie.view.adapter.SearchMovieAdapter;
import com.duc.themovie.view.adapter.SearchTVAdapter;
import com.duc.themovie.view.adapter.SearchPeopleAdapter;
import com.duc.themovie.viewmodel.SearchViewModel;

public class SearchFragment extends BaseFrg<SearchFragmentBinding, SearchViewModel> {

    private SearchMovieAdapter movieAdapter;
    private SearchTVAdapter tvAdapter;
    private SearchPeopleAdapter peopleAdapter;

    @Override
    protected Class<SearchViewModel> getClassViewModel() {
        return SearchViewModel.class;
    }

    @Override
    protected void initView() {
        // Setup RecyclerViews
        movieAdapter = new SearchMovieAdapter(viewModel.getListMovies(), context, navController);
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.rvMovies.setAdapter(movieAdapter);

        tvAdapter = new SearchTVAdapter(viewModel.getListTVShows(), context, navController);
        binding.rvTVShows.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.rvTVShows.setAdapter(tvAdapter);

        peopleAdapter = new SearchPeopleAdapter(viewModel.getListPeople(), context, navController);
        binding.rvPeople.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.rvPeople.setAdapter(peopleAdapter);

        // Search button click
        binding.btSearch.setOnClickListener(v -> doSearch());

        // Enter key on keyboard
        binding.edtSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                doSearch();
                return true;
            }
            return false;
        });

        // Expand/Collapse sections
        setupExpandCollapse();
    }

    private void doSearch() {
        String query = binding.edtSearch.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(context, "Please enter search query", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hide keyboard
        hideKeyboard();

        // Call API
        viewModel.searchMulti(query);
    }

    private void setupExpandCollapse() {
        // Movies expand/collapse
        binding.ivMovieExpand.setOnClickListener(v -> {
            if (binding.rvMovies.getVisibility() == View.VISIBLE) {
                binding.rvMovies.setVisibility(View.GONE);
                binding.ivMovieExpand.setRotation(0);
            } else {
                binding.rvMovies.setVisibility(View.VISIBLE);
                binding.ivMovieExpand.setRotation(180);
            }
        });

        // TV Shows expand/collapse
        binding.ivTVExpand.setOnClickListener(v -> {
            if (binding.rvTVShows.getVisibility() == View.VISIBLE) {
                binding.rvTVShows.setVisibility(View.GONE);
                binding.ivTVExpand.setRotation(0);
            } else {
                binding.rvTVShows.setVisibility(View.VISIBLE);
                binding.ivTVExpand.setRotation(180);
            }
        });

        // People expand/collapse
        binding.ivPeopleExpand.setOnClickListener(v -> {
            if (binding.rvPeople.getVisibility() == View.VISIBLE) {
                binding.rvPeople.setVisibility(View.GONE);
                binding.ivPeopleExpand.setRotation(0);
            } else {
                binding.rvPeople.setVisibility(View.VISIBLE);
                binding.ivPeopleExpand.setRotation(180);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(SearchViewModel.KEY_SEARCH_MULTI)) {
            // Update UI
            int movieCount = viewModel.getListMovies().size();
            int tvCount = viewModel.getListTVShows().size();
            int peopleCount = viewModel.getListPeople().size();

            // Movies
            if (movieCount > 0) {
                binding.layoutMovies.setVisibility(View.VISIBLE);
                binding.tvMovieCount.setText(String.valueOf(movieCount));
                binding.rvMovies.setVisibility(View.VISIBLE);
                binding.ivMovieExpand.setRotation(180);
                movieAdapter.notifyDataSetChanged();
            } else {
                binding.layoutMovies.setVisibility(View.GONE);
            }

            // TV Shows
            if (tvCount > 0) {
                binding.layoutTVShows.setVisibility(View.VISIBLE);
                binding.tvTVCount.setText(String.valueOf(tvCount));
                binding.rvTVShows.setVisibility(View.VISIBLE);
                binding.ivTVExpand.setRotation(180);
                tvAdapter.notifyDataSetChanged();
            } else {
                binding.layoutTVShows.setVisibility(View.GONE);
            }

            // People
            if (peopleCount > 0) {
                binding.layoutPeople.setVisibility(View.VISIBLE);
                binding.tvPeopleCount.setText(String.valueOf(peopleCount));
                binding.rvPeople.setVisibility(View.VISIBLE);
                binding.ivPeopleExpand.setRotation(180);
                peopleAdapter.notifyDataSetChanged();
            } else {
                binding.layoutPeople.setVisibility(View.GONE);
            }

            if (movieCount == 0 && tvCount == 0 && peopleCount == 0) {
                Toast.makeText(context, "No results found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected SearchFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return SearchFragmentBinding.inflate(inflater, container, false);
    }

    private void hideKeyboard() {
        if (getActivity() != null && getView() != null) {
            android.view.inputmethod.InputMethodManager imm =
                    (android.view.inputmethod.InputMethodManager) getActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
}