package com.duc.themovie.view.fragment;

import static com.duc.themovie.view.adapter.ItemAdapter.POSTER_PATH;
import static com.duc.themovie.viewmodel.DetailPersonVM.KEY_GET_DETAIL_PERSON;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.duc.themovie.api.res.DetailPersonRes;
import com.duc.themovie.databinding.PersonDetailFragmentBinding;
import com.duc.themovie.view.adapter.ImageAdapter;
import com.duc.themovie.viewmodel.DetailPersonVM;

public class DetailPersonFragment extends BaseFrg<PersonDetailFragmentBinding, DetailPersonVM> {
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
    protected PersonDetailFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return PersonDetailFragmentBinding.inflate(inflater, container, false);
    }

    @SuppressLint({"DefaultLocale", "NotifyDataSetChanged"})
    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(KEY_GET_DETAIL_PERSON)) {
            DetailPersonRes res = (DetailPersonRes) data;
            binding.tvName.setText(res.name);
            binding.tvKnowFor.setText(res.knownForDepartment);
            binding.tvGender.setText((res.gender == 1) ? "FeMale" : "Male");
            binding.tvBirthday.setText(res.birthday);
            binding.tvPlaceOfBirth.setText(res.placeOfBirth);
            binding.tvBiography.setText(res.biography);
            Glide.with(context).load(String.format("%s%s", POSTER_PATH, res.profilePath)).into(binding.ivProfile);
            imgAdpt.notifyDataSetChanged();
        }
        super.apiSuccess(key, data);
    }
}
