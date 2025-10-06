package com.duc.themovie.view.adapter;

import android.content.Context;
import android.os.Bundle;
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
import com.duc.themovie.api.res.PeopleRes;

import java.util.List;
import java.util.stream.Collectors;

public class PeopleApdapter extends RecyclerView.Adapter<PeopleApdapter.PersonHolder> {
    public static final String PROFILE_PATH = "https://media.themoviedb.org/t/p/w220_and_h330_face/";

    private List<PeopleRes.Result> people;
    private Context context;

    public PeopleApdapter(List<PeopleRes.Result> people, Context context) {
        this.people = people;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new PersonHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        PeopleRes.Result person = people.get(position);
        Glide.with(context).load(PROFILE_PATH + person.profilePath).into(holder.profile);
        holder.name.setText(person.name);
        if (person.knownFor != null && !person.knownFor.isEmpty()) {
            String credits = person.knownFor.stream()
                    .map(kf -> (kf.name != null && !kf.name.isEmpty()) ? kf.name : kf.title)
                    .collect(Collectors.joining(", "));
            holder.credits.setText(credits);
        } else {
            holder.credits.setText("");
        }
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("personId", person.id);
            Navigation.findNavController(v).navigate(R.id.detailPersonFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public static class PersonHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView credits;
        ImageView profile;

        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvPersonName);
            credits = itemView.findViewById(R.id.tvCreditCombined);
            profile = itemView.findViewById(R.id.ivProfile);
        }
    }
}
