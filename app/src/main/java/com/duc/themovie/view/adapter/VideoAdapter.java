package com.duc.themovie.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duc.themovie.R;
import com.duc.themovie.api.res.VideoRes;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private static final String IMG_PATH = "https://img.youtube.com/vi/";
    private static final String VIDEO_PATH = "https://www.youtube.com/watch?v=";
    private List<VideoRes.Result> videos;
    private Context context;

    public VideoAdapter(List<VideoRes.Result> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override

    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {

        VideoRes.Result video = (VideoRes.Result) videos.get(position);

        holder.title.setText(video.name);
        holder.releaseDate.setText(video.publishAt.substring(0, 10));
        Glide.with(context).load(IMG_PATH + video.key + "/0.jpg").into(holder.videoThumbnail);

        holder.itemView.setOnClickListener(v -> {
            String youtubeUrl = VIDEO_PATH + video.key;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView videoThumbnail;
        TextView title;
        TextView releaseDate;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.ivVideo);
            title = itemView.findViewById(R.id.tvTitle);
            releaseDate = itemView.findViewById(R.id.tvReleaseDate);
        }
    }
}
