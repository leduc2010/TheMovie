package com.duc.themovie.view.adapter;


import static com.duc.themovie.view.adapter.ItemAdapter.BACKDROP_PATH;
import static com.duc.themovie.view.adapter.ItemAdapter.POSTER_PATH;
import static com.duc.themovie.view.adapter.ItemAdapter.PROFILE_PATH;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duc.themovie.R;
import com.duc.themovie.api.res.ImageRes;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_POSTER = 0;
    private static final int TYPE_BACKDROP = 1;
    private static final int TYPE_PROFILE = 2;

    private List<?> imageList;
    private Context context;

    public ImageAdapter(List<?> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = imageList.get(position);
        if (item instanceof ImageRes.Poster) return TYPE_POSTER;
        else if (item instanceof ImageRes.Backdrop) return TYPE_BACKDROP;
        else return TYPE_PROFILE;
    }
        
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_POSTER) {
            View v = inflater.inflate(R.layout.item_poster, parent, false);
            return new PosterHolder(v);
        } else if (viewType == TYPE_BACKDROP){
            View v = inflater.inflate(R.layout.item_backdrop, parent, false);
            return new BackdropHolder(v);
        }else {
            View v = inflater.inflate(R.layout.item_profile, parent, false);
            return new ProfileHodler(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String path;
        if (holder instanceof PosterHolder) {
            ImageRes.Poster poster = (ImageRes.Poster) imageList.get(position);
            path = poster.filePath;
            Glide.with(context)
                    .load(String.format("%s%s", POSTER_PATH, path))
                    .into(((PosterHolder) holder).ivPoster);
        } else if(holder instanceof BackdropHolder){
            ImageRes.Backdrop backdrop = (ImageRes.Backdrop) imageList.get(position);
            path = backdrop.filePath;
            Glide.with(context)
                    .load(String.format("%s%s", BACKDROP_PATH , path))
                    .into(((BackdropHolder) holder).ivBackdrop);
        }else {
            ImageRes.Profile profile = (ImageRes.Profile) imageList.get(position);
            path = profile.filePath;
            Glide.with(context)
                    .load(String.format("%s%s", PROFILE_PATH , path))
                    .into(((ProfileHodler) holder).ivProfile);
        }
    }

    @Override
    public int getItemCount() {
        return imageList != null ? imageList.size() : 0;
    }

    static class PosterHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        PosterHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }
    }

    static class BackdropHolder extends RecyclerView.ViewHolder {
        ImageView ivBackdrop;
        BackdropHolder(@NonNull View itemView) {
            super(itemView);
            ivBackdrop = itemView.findViewById(R.id.ivBackdrop);
        }
    }

    static class ProfileHodler extends RecyclerView.ViewHolder {
        ImageView ivProfile;

        ProfileHodler(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
        }
    }
}
