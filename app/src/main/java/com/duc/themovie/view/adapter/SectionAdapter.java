package com.duc.themovie.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duc.themovie.R;
import com.duc.themovie.api.res.MovieRes;
import com.duc.themovie.model.Section;
import com.duc.themovie.view.SectionVM;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.List;

public class SectionAdapter<VM extends SectionVM> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_TRENDING = 1;
    private static final int VIEW_TYPE_NORMAL = 0;
    private Context context;
    private List<Section> sections;

    private final VM viewModel;

    public static final String TAG = SectionAdapter.class.getName();

    public SectionAdapter(Context context, List<Section> sections, VM viewModel) {
        this.context = context;
        this.sections = sections;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TRENDING) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_favoritemovie, parent, false);
            return new TrendingViewHolder(v);
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sections, parent, false);
        return new SectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Section section = sections.get(position);

        String type = section.getType();
        String key = viewModel.getKeyBySectionType(type);

        if (holder.getItemViewType() == VIEW_TYPE_TRENDING) {
            TrendingViewHolder trendingViewHolder = (TrendingViewHolder) holder;
            trendingViewHolder.tvSectionTitle.setText(section.getTitle());

            List<?> currentList = viewModel.getResultList(key);
            boolean isMovie = type.contains("Movie");

            if (trendingViewHolder.trendingAdapter == null) {
                trendingViewHolder.trendingAdapter = new ItemAdapter<>(currentList, context, isMovie);
                trendingViewHolder.rvTrendingMovies.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                trendingViewHolder.rvTrendingMovies.setAdapter(trendingViewHolder.trendingAdapter);
            } else {
                trendingViewHolder.trendingAdapter.updateListResult(currentList);
                trendingViewHolder.trendingAdapter.setMovie(isMovie);
                trendingViewHolder.trendingAdapter.notifyDataSetChanged();
            }

            initToggle(trendingViewHolder, trendingViewHolder.trendingAdapter, type);

        } else {
            SectionViewHolder normalHolder = (SectionViewHolder) holder;
            normalHolder.tvSectionTitle.setText(section.getTitle());

            List<?> list = viewModel.getResultList(key);
            boolean isMovie = type.contains("Movie");

            normalHolder.sectionAdapter = new ItemAdapter<>(list, context, isMovie);
            normalHolder.rvSections.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            normalHolder.rvSections.setAdapter(normalHolder.sectionAdapter);

            normalHolder.btnAll.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("sectionType", type);
                Navigation.findNavController(v).navigate(R.id.allItemFragment, bundle);
            });
        }
    }

    private void initToggle(TrendingViewHolder trendingViewHolder, ItemAdapter adapter, String type) {
        String current = viewModel.getTimeWindow();
        if ("week".equals(current)) {
            trendingViewHolder.toggleGroup.check(R.id.btnWeek);
        } else {
            trendingViewHolder.toggleGroup.check(R.id.btnDay);
        }

        trendingViewHolder.toggleGroup.clearOnButtonCheckedListeners();
        trendingViewHolder.toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btnDay) {
                    handleToggleChanged("day", trendingViewHolder.btnDay, trendingViewHolder.btnWeek, trendingViewHolder, adapter, type);
                } else if (checkedId == R.id.btnWeek) {
                    handleToggleChanged("week", trendingViewHolder.btnWeek, trendingViewHolder.btnDay, trendingViewHolder, adapter, type);
                }
            }
        });
    }

    private void handleToggleChanged(String timeWindow, MaterialButton selectedBtn, MaterialButton unselectedBtn,
                                     TrendingViewHolder trendingViewHolder, ItemAdapter adapter, String type) {
        setButtonSelected(selectedBtn, true);
        setButtonSelected(unselectedBtn, false);
        String key = viewModel.getKeyBySectionType(type);
        viewModel.clearResultList(key);
        viewModel.resetPage(key);
        viewModel.setTimeWindow(timeWindow);
        viewModel.getListTrending();
        adapter.updateListResult(viewModel.getResultList(key));
        trendingViewHolder.rvTrendingMovies.smoothScrollToPosition(0);
    }

    private void setButtonSelected(MaterialButton btn, boolean selected) {
        if (selected) {
            btn.setIconTintResource(R.color.cyan);
            btn.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_check));
            btn.setIconTintResource(android.R.color.white);
        } else {
            btn.setIconTintResource(android.R.color.black);
            btn.setIcon(null);
        }
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    @Override
    public int getItemViewType(int position) {
        Section section = sections.get(position);
        if (section.getType().equals("TrendingMovie") || section.getType().equals("TrendingTVShows")) {
            return VIEW_TYPE_TRENDING;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    public static class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView tvSectionTitle;
        RecyclerView rvSections;
        ItemAdapter sectionAdapter;
        MaterialButton btnAll;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSectionTitle = itemView.findViewById(R.id.tvSectionTitle);
            rvSections = itemView.findViewById(R.id.rvMovies);
            btnAll = itemView.findViewById(R.id.bt_all);
        }
    }

    private static class TrendingViewHolder extends RecyclerView.ViewHolder {
        TextView tvSectionTitle;
        RecyclerView rvTrendingMovies;
        MaterialButtonToggleGroup toggleGroup;
        ItemAdapter trendingAdapter;

        MaterialButton btnDay, btnWeek;

        TrendingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSectionTitle = itemView.findViewById(R.id.tvSectionTitle);
            rvTrendingMovies = itemView.findViewById(R.id.rvTrendingMovies);
            toggleGroup = itemView.findViewById(R.id.toggleGroup);
            btnDay = itemView.findViewById(R.id.btnDay);
            btnWeek = itemView.findViewById(R.id.btnWeek);
        }
    }
}
