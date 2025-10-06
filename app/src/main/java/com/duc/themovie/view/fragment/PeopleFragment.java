package com.duc.themovie.view.fragment;

import static com.duc.themovie.viewmodel.PeopleVM.KEY_GET_PEOPLE;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duc.themovie.databinding.PeopleFragmentBinding;
import com.duc.themovie.view.adapter.PeopleApdapter;
import com.duc.themovie.viewmodel.PeopleVM;

public class PeopleFragment extends BaseFrg<PeopleFragmentBinding, PeopleVM> {

    private PeopleApdapter peopleAdpt;

    @Override
    protected Class<PeopleVM> getClassViewModel() {
        return PeopleVM.class;
    }

    @Override
    protected void initView() {
        viewModel.getPopularPeople();
        peopleAdpt = new PeopleApdapter(viewModel.getPeople(), context);
        binding.rvPeople.setLayoutManager(new GridLayoutManager(context, 3));
        binding.rvPeople.setAdapter(peopleAdpt);
        binding.rvPeople.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.getPopularPeople();
                }
            }
        });
    }

    @Override
    protected PeopleFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return PeopleFragmentBinding.inflate(inflater, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(KEY_GET_PEOPLE)) {
            peopleAdpt.notifyDataSetChanged();
        }
        super.apiSuccess(key, data);
    }
}
