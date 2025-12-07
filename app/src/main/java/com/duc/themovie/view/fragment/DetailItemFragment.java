package com.duc.themovie.view.fragment;

import static android.graphics.Typeface.BOLD;
import static com.duc.themovie.view.adapter.ItemAdapter.BACKDROP_PATH;
import static com.duc.themovie.view.adapter.ItemAdapter.POSTER_PATH;
import static com.duc.themovie.viewmodel.DetailItemVM.KEY_GET_CREDIT_MOVIE;
import static com.duc.themovie.viewmodel.DetailItemVM.KEY_GET_DETAIL_MOVIE;
import static com.duc.themovie.viewmodel.DetailItemVM.KEY_GET_DETAIL_TV_SHOW;
import static com.duc.themovie.viewmodel.DetailItemVM.KEY_GET_MOVIE_KEYWORDS;
import static com.duc.themovie.viewmodel.DetailItemVM.KEY_GET_TVSHOW_CREDITS;
import static com.duc.themovie.viewmodel.DetailItemVM.KEY_GET_TVSHOW_KEYWORDS;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.duc.themovie.R;
import com.duc.themovie.api.res.DetailMovieRes;
import com.duc.themovie.api.res.DetailTVShowRes;
import com.duc.themovie.api.res.KeywordsMovieRes;
import com.duc.themovie.api.res.KeywordsTVRes;
import com.duc.themovie.databinding.DetailItemFragmentBinding;
import com.duc.themovie.view.adapter.CastAdapter;
import com.duc.themovie.view.adapter.MediaPagerAdapter;
import com.duc.themovie.view.adapter.RecommnedationAdapter;
import com.duc.themovie.viewmodel.DetailItemVM;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailItemFragment extends BaseFrg<DetailItemFragmentBinding, DetailItemVM> {

    private static final String LOGO_PATH = "https://image.tmdb.org/t/p/w500";
    private CastAdapter castAdpt;

    @Override
    protected Class<DetailItemVM> getClassViewModel() {
        return DetailItemVM.class;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        if (getArguments() != null) {
            int itemId = getArguments().getInt("itemId");
            String itemType = getArguments().getString("itemType");
            initTabLayout(itemId, itemType);

            if (itemType != null && itemType.equals("movies") || itemType.equals("movie")) {
                binding.lnAboutMovie.setVisibility(View.VISIBLE);
                binding.lnAboutTVShow.setVisibility(View.GONE);
                binding.tvCast.setText("Top Billed Cast");
                viewModel.getMovieDetail(itemId);
                viewModel.getMovieCredits(itemId);

                castAdpt = new CastAdapter(viewModel.getCastList(), context);
                binding.rvCast.setAdapter(castAdpt);

                viewModel.getMovieKeywords(itemId);
            } else {
                binding.lnAboutMovie.setVisibility(View.GONE);
                binding.lnAboutTVShow.setVisibility(View.VISIBLE);
                binding.tvCast.setText("Series Cast");
                viewModel.getTVDetail(itemId);
                viewModel.getTVShowCredits(itemId);

                castAdpt = new CastAdapter(viewModel.getCastList(), context);
                binding.rvCast.setAdapter(castAdpt);
                viewModel.getTVShowKeywords(itemId);
            }
        }
        binding.tvOverviewContent.setAnimationDuration(200);
        binding.tvOverviewContent.setOnClickListener(v -> binding.tvOverviewContent.toggle());

        binding.ivBack.setOnClickListener(v -> navController.popBackStack());
    }

    private void initTabLayout(int itemId, String itemType) {
        MediaPagerAdapter mediaAdpt = new MediaPagerAdapter((FragmentActivity) context, itemId, itemType);
        binding.vpDetail.setAdapter(mediaAdpt);
        binding.vpDetail.setUserInputEnabled(false);

        new TabLayoutMediator(binding.tlDetail, binding.vpDetail,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Videos");
                            break;
                        case 1:
                            tab.setText("Posters");
                            break;
                        case 2:
                            tab.setText("Backdrops");
                            break;
                    }
                }).attach();

        RecommnedationAdapter recAdpt = new RecommnedationAdapter((FragmentActivity) context, itemId, itemType);
        binding.vpRecommendation.setAdapter(recAdpt);
        binding.vpRecommendation.setUserInputEnabled(false);

        new TabLayoutMediator(binding.tlRecommendation, binding.vpRecommendation,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Recommendations");
                            break;
                        case 1:
                            tab.setText("Similar " + itemType);
                            break;
                    }
                }).attach();
    }


    @Override
    protected DetailItemFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return DetailItemFragmentBinding.inflate(inflater, container, false);
    }

    @SuppressLint({"DefaultLocale", "NotifyDataSetChanged"})
    @Override
    public void apiSuccess(String key, Object data) {
        switch (key) {
            case KEY_GET_DETAIL_MOVIE: {
                initMovieDetail(data);
                break;
            }
            case KEY_GET_DETAIL_TV_SHOW: {
                initTVShowsDetail(data);
                break;
            }
            case KEY_GET_CREDIT_MOVIE:
            case KEY_GET_TVSHOW_CREDITS:
                castAdpt.notifyDataSetChanged();
                break;
            case KEY_GET_MOVIE_KEYWORDS: {
                KeywordsMovieRes res = (KeywordsMovieRes) data;
                addKeywords(res);
                break;
            }
            case KEY_GET_TVSHOW_KEYWORDS: {
                KeywordsTVRes res = (KeywordsTVRes) data;
                addKeywords(res);
                break;
            }
        }
        super.apiSuccess(key, data);
    }

    private void initTVShowsDetail(Object data) {
        DetailTVShowRes res = (DetailTVShowRes) data;
        Glide.with(context).load(String.format("%s%s", POSTER_PATH, res.posterPath)).into(binding.ivPoster);
        Glide.with(context).load(String.format("%s%s", BACKDROP_PATH, res.backdropPath)).into(binding.ivBgDetail);
        binding.tvName.setText(res.name);
        binding.tvReleaseDate.setText(res.firstAirDate);
        if (res.runtime > 0) {
            binding.tvRuntime.setVisibility(View.VISIBLE);
            binding.tvRuntime.setText(formatRuntime(res.runtime));
        } else {
            binding.tvRuntime.setVisibility(View.GONE);
        }
        binding.tvVoteAverage.setText(String.format("%.1f", res.voteAverage));
        binding.tvVoteCount.setText(String.valueOf(res.voteCount));
        addGenres(res);
        binding.tvOverviewContent.setText(res.overview);
        binding.tvTVType.setText(res.type);
        addCreator(res);
        addNetWorks(res);
        binding.tvTVStatus.setText(res.status);
        binding.tvTVOgLanguage.setText(res.originalLanguage);
        binding.tvTVNumberOfSeasons.setText(String.valueOf(res.numberOfSeasons));
        binding.tvTVNumberOfEpisodes.setText(String.valueOf(res.numberOfEpisodes));
        binding.tvTVCountry.setText(res.productionCountries.get(0).name);
        addCompanies(res);
    }

    private void initMovieDetail(Object data) {
        DetailMovieRes res = (DetailMovieRes) data;
        Glide.with(context).load(String.format("%s%s", POSTER_PATH, res.posterPath)).into(binding.ivPoster);
        Glide.with(context).load(String.format("%s%s", BACKDROP_PATH, res.backdropPath)).into(binding.ivBgDetail);
        binding.tvName.setText(res.title);
        binding.tvReleaseDate.setText(res.releaseDate);
        binding.tvRuntime.setVisibility(View.VISIBLE);
        binding.tvRuntime.setText(formatRuntime(res.runtime));
        binding.tvVoteAverage.setText(String.format("%.1f", res.voteAverage));
        binding.tvVoteCount.setText(String.valueOf(res.voteCount));
        binding.tvOverviewContent.setText(res.overview);
        addGenres(res);
        binding.tvMovieStatus.setText(res.status);
        binding.tvOriginalLanguage.setText(res.originalLanguage);
        setNumber(binding.tvBudget, res.budget);
        setNumber(binding.tvRevenue, res.revenue);
        binding.tvMovieCountry.setText(res.productionCountries.get(0).name);
        addCompanies(res);
    }

    private void addNetWorks(DetailTVShowRes res) {
        if (res.networks != null) {
            binding.flexNetwork.removeAllViews();
            for (DetailTVShowRes.Network network : res.networks) {
                ImageView iv = new ImageView(getContext());

                int width = (int) (getResources().getDisplayMetrics().density * 80);
                int height = (int) (getResources().getDisplayMetrics().density * 40);
                FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(width, height);
                lp.setMargins(8, 8, 8, 8);
                iv.setLayoutParams(lp);
                iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                int radius = (int) (getResources().getDisplayMetrics().density * 10);

                Glide.with(context).load(LOGO_PATH + network.logoPath)
                        .transform(new RoundedCorners(radius))
                        .into(iv);
                binding.flexNetwork.addView(iv);
            }
        }
    }

    private void addCreator(DetailTVShowRes res) {
        if (res.createdBy != null) {
            binding.flexTVShowCreators.removeAllViews();
            for (DetailTVShowRes.Creator creator : res.createdBy) {
                TextView tv = new TextView(requireContext());

                tv.setText(creator.name);
                tv.setPadding(20, 10, 20, 10);
                tv.setTextSize(14);
                tv.setTypeface(null, BOLD);
                tv.setTextColor(ContextCompat.getColor(context, R.color.white));

                binding.flexTVShowCreators.addView(tv);
            }
        } else {
            binding.trCreator.setVisibility(View.GONE);
        }
    }

    private void addCompanies(DetailMovieRes res) {
        binding.flexMovieCompanies.removeAllViews();
        if (res.productionCompanies != null) {
            for (DetailMovieRes.ProductionCompanies company : res.productionCompanies) {
                TextView tv = new TextView(requireContext());
                tv.setText(company.name);
                tv.setPadding(20, 10, 20, 10);
                tv.setTextSize(14);
                tv.setTypeface(null, BOLD);
                tv.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_linear));
                tv.setTextColor(ContextCompat.getColor(context, R.color.light_blue));
                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 8, 8, 8);
                tv.setLayoutParams(params);
                binding.flexMovieCompanies.addView(tv);
            }
        }
    }

    private void addCompanies(DetailTVShowRes res) {
        binding.flexTVCompanies.removeAllViews();
        if (res.productionCompanies != null) {
            for (DetailTVShowRes.ProductionCompany company : res.productionCompanies) {
                TextView tv = new TextView(requireContext());
                tv.setText(company.name);
                tv.setPadding(20, 10, 20, 10);
                tv.setTextSize(14);
                tv.setTypeface(null, BOLD);
                tv.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_linear));
                tv.setTextColor(ContextCompat.getColor(context, R.color.light_blue));
                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 8, 8, 8);
                tv.setLayoutParams(params);
                binding.flexTVCompanies.addView(tv);
            }
        }
    }

    private void addKeywords(KeywordsMovieRes res) {
        binding.flexKeywords.removeAllViews();
        if (res.keywords != null) {
            for (KeywordsMovieRes.Keyword keyword : res.keywords) {
                TextView tv = new TextView(requireContext());
                tv.setText(keyword.name);
                tv.setPadding(20, 10, 20, 10);
                tv.setTextSize(14);
                tv.setTypeface(null, BOLD);
                tv.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_linear));
                tv.setTextColor(ContextCompat.getColor(context, R.color.light_blue));
                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 8, 8, 8);
                tv.setLayoutParams(params);
                binding.flexKeywords.addView(tv);
            }
        }
    }

    private void addKeywords(KeywordsTVRes res) {
        binding.flexKeywords.removeAllViews();
        if (res.results != null) {
            for (KeywordsTVRes.Keyword keyword : res.results) {
                TextView tv = new TextView(requireContext());
                tv.setText(keyword.name);
                tv.setPadding(20, 10, 20, 10);
                tv.setTextSize(14);
                tv.setTypeface(null, BOLD);
                tv.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_linear));
                tv.setTextColor(ContextCompat.getColor(context, R.color.light_blue));
                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 8, 8, 8);
                tv.setLayoutParams(params);
                binding.flexKeywords.addView(tv);
            }
        }
    }

    private void addGenres(DetailMovieRes res) {
        binding.flexGenres.removeAllViews();

        if (res.genres != null) {
            for (DetailMovieRes.Genre genre : res.genres) {
                TextView tv = new TextView(requireContext());
                tv.setText(genre.name);
                tv.setPadding(30, 10, 30, 10);
                tv.setTextSize(14);
                tv.setTypeface(null, BOLD);
                tv.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_linear));
                tv.setTextColor(ContextCompat.getColor(context, R.color.light_blue));
                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 8, 8, 8);
                tv.setLayoutParams(params);
                binding.flexGenres.addView(tv);
            }
        }
    }

    private void addGenres(DetailTVShowRes res) {
        binding.flexGenres.removeAllViews();

        if (res.genres != null) {
            for (DetailTVShowRes.Genre genre : res.genres) {
                TextView tv = new TextView(requireContext());
                tv.setText(genre.name);
                tv.setPadding(30, 10, 30, 10);
                tv.setTextSize(14);
                tv.setTypeface(null, BOLD);
                tv.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_linear));
                tv.setTextColor(ContextCompat.getColor(context, R.color.light_blue));
                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 8, 8, 8);
                tv.setLayoutParams(params);
                binding.flexGenres.addView(tv);
            }
        }
    }

    private void setNumber(TextView tv, int value) {
        if (value == 0) {
            tv.setText("_");
        } else {
            tv.setText(String.format("$%s", NumberFormat.getInstance(Locale.US).format(value)));
        }
    }

    private String formatRuntime(int runtime) {
        int hours = runtime / 60;
        int minutes = runtime % 60;
        return hours + "h" + minutes + "m";
    }
}
