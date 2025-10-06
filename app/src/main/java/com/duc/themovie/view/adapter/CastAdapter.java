package com.duc.themovie.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duc.themovie.R;
import com.duc.themovie.api.res.CreditRes;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder> {
    public static final String PROFILE_PATH = "https://media.themoviedb.org/t/p/w220_and_h330_face/";

    private List<CreditRes.Cast> castList;
    private Context context;

    public CastAdapter(List<CreditRes.Cast> castList, Context context) {
        this.castList = castList;
        this.context = context;
    }

    @NonNull
    @Override
    public CastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new CastHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CastHolder holder, int position) {
        CreditRes.Cast cast = castList.get(position);
        holder.castName.setText(cast.originalName);
        holder.charName.setText(cast.character);
        Glide.with(context).load(PROFILE_PATH + cast.profilePath).into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class CastHolder extends RecyclerView.ViewHolder {
        TextView castName;
        TextView charName;
        ImageView profile;

        public CastHolder(@NonNull View itemView) {
            super(itemView);
            castName = itemView.findViewById(R.id.tvCastName);
            charName = itemView.findViewById(R.id.tvCharacterName);
            profile = itemView.findViewById(R.id.ivProfile);
        }
    }
}
