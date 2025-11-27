    package com.duc.themovie.view.adapter;

    import android.annotation.SuppressLint;
    import android.content.Context;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.navigation.Navigation;
    import androidx.recyclerview.widget.RecyclerView;

    import com.bumptech.glide.Glide;
    import com.duc.themovie.R;
    import com.duc.themovie.api.res.MovieRes;
    import com.duc.themovie.api.res.TVShowRes;

    import java.util.List;
    import java.util.Locale;

    public class ItemAdapter<T> extends RecyclerView.Adapter<ItemAdapter.MovieHolder> {
        public static final String POSTER_PATH = "https://media.themoviedb.org/t/p/w220_and_h330_face/";
        public static final String BACKDROP_PATH = "https://image.tmdb.org/t/p/original/";
        public static final String PROFILE_PATH = "https://media.themoviedb.org/t/p/w220_and_h330_face/";

        private List<T> items;
        private Context context;
        private boolean isMovie = true;

        public void setMovie(boolean movie) {
            isMovie = movie;
        }

        public ItemAdapter(List<T> items, Context context, boolean isMovie) {
            this.items = items;
            this.context = context;
            this.isMovie = isMovie;
        }

        @NonNull
        @Override
        public ItemAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
            return new MovieHolder(v);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ItemAdapter.MovieHolder holder, int position) {
            if (isMovie) {
                MovieRes.Result item = (MovieRes.Result) items.get(position);
                holder.tvTitle.setText(item.title);
                holder.tvVote.setText(String.format(Locale.US, "%.1f", item.voteAverage));
                if (item.releaseDate != null && item.releaseDate.length() >= 4) {
                    holder.tvYear.setText(item.releaseDate.substring(0, 4));
                } else {
                    holder.tvYear.setText("N/A");
                }
                Glide.with(context).load(String.format("%s%s", POSTER_PATH, item.posterPath)).into(holder.ivPhoto);

                holder.itemView.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("itemId", item.id);
                    bundle.putString("itemType", "movies");
                    Navigation.findNavController(v).navigate(R.id.detailItemFragment, bundle);
                });
            } else {
                TVShowRes.Result item = (TVShowRes.Result) items.get(position);
                Log.d("TVDebug", "TopRated item: name=" + item.name + ", originalName=" + item.name + ", firstAirDate=" + item.firstAirDate);
                holder.tvTitle.setText(item.name);
                holder.tvVote.setText(String.format(Locale.US, "%.1f", item.voteAverage));
                if (item.firstAirDate != null && item.firstAirDate.length() >= 4) {
                    holder.tvYear.setText(item.firstAirDate.substring(0, 4));
                } else {
                    holder.tvYear.setText("N/A");
                }
                Glide.with(context).load(String.format("%s%s", POSTER_PATH, item.posterPath)).into(holder.ivPhoto);
                holder.itemView.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("itemId", item.id);
                    bundle.putString("itemType", "TV shows");
                    Navigation.findNavController(v).navigate(R.id.detailItemFragment, bundle);
                });
            }
        }

        @Override
        public int getItemCount() {
            return items != null ? items.size() : 0;
        }

        @SuppressLint("NotifyDataSetChanged")
        public void updateListResult(List<T> newItems) {
            this.items = newItems;
            notifyDataSetChanged();
        }

        public void updateData(List<T> newList) {
            int oldSize = this.items.size();
            this.items.clear();
            this.items.addAll(newList);
            notifyItemRangeInserted(oldSize, newList.size() - oldSize);
        }

        public static class MovieHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvVote, tvYear;
            ImageView ivPhoto;

            public MovieHolder(@NonNull View itemView) {
                super(itemView);
                ivPhoto = itemView.findViewById(R.id.ivPoster);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvYear = itemView.findViewById(R.id.tvYear);
                tvVote = itemView.findViewById(R.id.tvVote);
            }
        }
    }
