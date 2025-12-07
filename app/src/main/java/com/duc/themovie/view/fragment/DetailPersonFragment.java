package com.duc.themovie.view.fragment;

import static com.duc.themovie.view.adapter.ItemAdapter.POSTER_PATH;
import static com.duc.themovie.viewmodel.DetailPersonVM.KEY_GET_COMBINED_CREDITS;
import static com.duc.themovie.viewmodel.DetailPersonVM.KEY_GET_DETAIL_PERSON;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.duc.themovie.R;
import com.duc.themovie.api.res.DetailPersonRes;
import com.duc.themovie.databinding.DetailPersonFragmentBinding;
import com.duc.themovie.view.adapter.ImageAdapter;
import com.duc.themovie.viewmodel.DetailPersonVM;

import java.util.Objects;

public class DetailPersonFragment extends BaseFrg<DetailPersonFragmentBinding, DetailPersonVM> {
    private ImageAdapter imgAdpt;
    private static final String LOGO_PATH = "https://image.tmdb.org/t/p/w500";

    @Override
    protected Class<DetailPersonVM> getClassViewModel() {
        return DetailPersonVM.class;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        if (getArguments() != null) {
            int personId = getArguments().getInt("personId");
            viewModel.getPersonDetail(personId);
            viewModel.getProfiles(personId);
            viewModel.getCombinedCredits(personId);
        }
        binding.ivBack.setOnClickListener(v -> navController.popBackStack());

        imgAdpt = new ImageAdapter(viewModel.getListProfile(), context);
        binding.rvProfile.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        );
        binding.rvProfile.setAdapter(imgAdpt);
        binding.tvBiography.setAnimationDuration(200);
        binding.tvBiography.setOnClickListener(v -> binding.tvBiography.toggle());
    }

    @Override
    protected DetailPersonFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DetailPersonFragmentBinding.inflate(inflater, container, false);
    }

    @SuppressLint({"DefaultLocale", "NotifyDataSetChanged"})
    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(KEY_GET_DETAIL_PERSON)) {
            DetailPersonRes res = (DetailPersonRes) data;
            binding.tvName.setText(res.name);
            binding.tvKnowFor.setText(res.knownForDepartment);
            binding.tvGender.setText((res.gender == 1) ? "Female" : "Male");
            binding.tvBirthday.setText(res.birthday!=null? res.birthday : "N/A");
            binding.tvPlaceOfBirth.setText(res.placeOfBirth);
            binding.tvBiography.setText(!Objects.equals(res.biography, "") ? res.biography : "N/A");
            Glide.with(context).load(String.format("%s%s", POSTER_PATH, res.profilePath)).into(binding.ivProfile);

        } else if (key.equals(KEY_GET_COMBINED_CREDITS)) {
            binding.lvLeft.removeAllViews();
            binding.lvRight.removeAllViews();

            String[] order = {"Acting", "Crew", "Directing", "Production", "Creator",
                    "Writing", "Camera", "Sound", "Art", "Editing", "Lighting", "Visual"};

            TextView tvMovie = new TextView(context);
            tvMovie.setText("Movie");
            tvMovie.setTextColor(Color.WHITE);
            tvMovie.setTextSize(16);
            tvMovie.setPadding(0, 8, 0, 10);
            binding.lvLeft.addView(tvMovie);

            TextView tvTVShow = new TextView(context);
            tvTVShow.setText("TV Show");
            tvTVShow.setTextColor(Color.WHITE);
            tvTVShow.setTextSize(16);
            tvTVShow.setPadding(0, 8, 0, 10);
            binding.lvRight.addView(tvTVShow);
            for (String dept : order) {
                int movie = viewModel.mapCombinedCred.getOrDefault(dept + "_Movie", 0);
                int tv = viewModel.mapCombinedCred.getOrDefault(dept + "_TV", 0);

                // Bỏ qua nếu cả Movie và TV đều = 0
                if (movie == 0 && tv == 0) continue;

                if (movie > 0) {
                    TextView tvMovie1 = new TextView(context);
                    tvMovie1.setText(dept +": " + movie);
                    tvMovie1.setTextColor(Color.WHITE);
                    tvMovie1.setTextSize(14);
                    tvMovie1.setPadding(0, 8, 0, 8);
                    binding.lvLeft.addView(tvMovie1);
                }

                if (tv > 0) {
                    TextView tvTV = new TextView(context);
                    tvTV.setText(dept + ": " +  tv);
                    tvTV.setTextColor(Color.WHITE);
                    tvTV.setTextSize(14);
                    tvTV.setPadding(0, 8, 0, 8);
                    binding.lvRight.addView(tvTV);
                }
            }
        }
        imgAdpt.notifyDataSetChanged();
    }
}