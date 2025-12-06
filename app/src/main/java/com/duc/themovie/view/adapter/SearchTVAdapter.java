package com.duc.themovie.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duc.themovie.R;
import com.duc.themovie.api.res.MultiSearchRes;

import java.util.List;

public class SearchTVAdapter extends RecyclerView.Adapter<SearchTVAdapter.ViewHolder> {

    private final List<MultiSearchRes.Result> listTVShows;
    private final Context context;
    private final NavController navController;
    private static final String POSTER_PATH = "https://image.tmdb.org/t/p/w500";

    public SearchTVAdapter(List<MultiSearchRes.Result> listTVShows, Context context, NavController navController) {
        this.listTVShows = listTVShows;
        this.context = context;
        this.navController = navController;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_movie, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MultiSearchRes.Result tv = listTVShows.get(position);

        // Load poster
        Glide.with(context)
                .load(POSTER_PATH + tv.posterPath)
                .placeholder(R.drawable.ic_placeholder)
                .into(holder.ivPoster);

        // Set title
        holder.tvTitle.setText(tv.title);

        // Set rating
        if (tv.voteAverage > 0) {
            holder.tvRating.setText(String.format("★ %.1f", tv.voteAverage));
            holder.tvRating.setVisibility(View.VISIBLE);
        } else {
            holder.tvRating.setVisibility(View.GONE);
        }

        // Set release year
        if (tv.releaseDate != null && !tv.releaseDate.isEmpty()) {
            String year = tv.releaseDate.substring(0, 4);
            holder.tvYear.setText(year);
            holder.tvYear.setVisibility(View.VISIBLE);
        } else {
            holder.tvYear.setVisibility(View.GONE);
        }

        // Click listener
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("itemId", tv.id);
            bundle.putString("itemType", "tv");  // Hoặc "tvshows" nếu DetailItemFragment dùng "tvshows"
            navController.navigate(R.id.detailItemFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listTVShows.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvRating, tvYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvYear = itemView.findViewById(R.id.tvYear);
        }
    }
}