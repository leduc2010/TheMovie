package com.duc.themovie.view.adapter;

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

public class SearchPeopleAdapter extends RecyclerView.Adapter<SearchPeopleAdapter.ViewHolder> {

    private final List<MultiSearchRes.Result> listPeople;
    private final Context context;
    private final NavController navController;
    private static final String POSTER_PATH = "https://image.tmdb.org/t/p/w500";

    public SearchPeopleAdapter(List<MultiSearchRes.Result> listPeople, Context context, NavController navController) {
        this.listPeople = listPeople;
        this.context = context;
        this.navController = navController;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_people, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MultiSearchRes.Result person = listPeople.get(position);

        // Debug log
        android.util.Log.d("SearchPeople", "===================");
        android.util.Log.d("SearchPeople", "Position: " + position);
        android.util.Log.d("SearchPeople", "Person ID: " + person.id);
        android.util.Log.d("SearchPeople", "Person name: " + person.title);
        android.util.Log.d("SearchPeople", "Profile path: " + person.profilePath);
        android.util.Log.d("SearchPeople", "Media type: " + person.mediaType);
        android.util.Log.d("SearchPeople", "Known for: " + person.knownForDepartment);
        // Load profile image (circular)
        Glide.with(context)
                .load(POSTER_PATH + person.profilePath)
                .placeholder(R.drawable.ic_placeholder)
                .circleCrop()
                .into(holder.ivProfile);

        // Set name
        holder.tvName.setText(person.title);

        // Set known for department
        if (person.knownForDepartment != null && !person.knownForDepartment.isEmpty()) {
            holder.tvDepartment.setText(person.knownForDepartment);
            holder.tvDepartment.setVisibility(View.VISIBLE);
        } else {
            holder.tvDepartment.setVisibility(View.GONE);
        }

        // Click listener
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("personId", person.id);
            navController.navigate(R.id.detailPersonFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return listPeople.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvName, tvDepartment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvDepartment = itemView.findViewById(R.id.tvDepartment);
        }
    }
}