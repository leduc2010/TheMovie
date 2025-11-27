package com.duc.themovie.api;


import com.duc.themovie.api.request.AccountReq;
import com.duc.themovie.api.request.RequestTokenReq;
import com.duc.themovie.api.res.AuthenRes;
import com.duc.themovie.api.res.CombinedCreditsRes;
import com.duc.themovie.api.res.CreditRes;
import com.duc.themovie.api.res.DetailMovieRes;
import com.duc.themovie.api.res.DetailPersonRes;
import com.duc.themovie.api.res.DetailTVShowRes;
import com.duc.themovie.api.res.ImageRes;
import com.duc.themovie.api.res.KeywordsMovieRes;
import com.duc.themovie.api.res.KeywordsTVRes;
import com.duc.themovie.api.res.MovieRes;
import com.duc.themovie.api.res.PeopleRes;
import com.duc.themovie.api.res.SessionRes;
import com.duc.themovie.api.res.TVShowRes;
import com.duc.themovie.api.res.VideoRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    static String API_KEY = "7f5991ebef61d8216bf4fc83d650e904";

    @GET("authentication/token/new?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<AuthenRes> getAuthen();

    @POST("authentication/token/validate_with_login?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<AuthenRes> createSession(@Body AccountReq accountReq);

    @POST("authentication/session/new?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<SessionRes> createSessionID(@Body RequestTokenReq tokenReq);

    @GET("trending/movie/{time_window}?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<MovieRes> getTrendingMovies(@Path("time_window") String timeWindow, @Query("page") int page);

    @GET("movie/popular?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<MovieRes> getPopularMovies(@Query("page") int page);

    @GET("tv/popular?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<TVShowRes> getPopularTVShows(@Query("page") int page);

    @GET("movie/now_playing?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<MovieRes> getNowPlayingMovies(@Query("page") int page);

    @GET("movie/upcoming?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<MovieRes> getUpcomingMovies(@Query("page") int page);

    @GET("movie/top_rated?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<MovieRes> getTopRatedMovies(@Query("page") int page);

    @GET("trending/tv/{time_window}?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<TVShowRes> getTrendingTVShows(@Path("time_window") String timeWindow, @Query("page") int page);

    @GET("tv/top_rated?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<TVShowRes> getTopRatedTVShows(@Query("page") int page);

    @GET("tv/airing_today?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<TVShowRes> getAiringTodayTVShows(@Query("page") int page);

    @GET("tv/on_the_air?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<TVShowRes> getOnTheAirTVShows(@Query("page") int page);

    @GET("movie/{id}?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<DetailMovieRes> getDetailMovie(@Path("id") int id);

    @GET("tv/{id}?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<DetailTVShowRes> getDetailTVShow(@Path("id") int id);

    @GET("movie/{id}/videos?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<VideoRes> getVideoMovies(@Path("id") int id);

    @GET("tv/{id}/videos?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<VideoRes> getVideoTVs(@Path("id") int id);

    @GET("movie/{id}/recommendations?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<MovieRes> getRecommendationMovies(@Path("id") int id);

    @GET("tv/{id}/recommendations?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<TVShowRes> getRecommendationTVs(@Path("id") int id);

    @GET("movie/{id}/similar?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<MovieRes> getSimilarMovies(@Path("id") int id);

    @GET("tv/{id}/similar?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<TVShowRes> getSimilarTVs(@Path("id") int id);

    @GET("movie/{id}/images?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<ImageRes> getImageMovie(@Path("id") int id);

    @GET("tv/{id}/images?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<ImageRes> getImageTV(@Path("id") int id);

    @GET("movie/{id}/credits?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<CreditRes> getMovieCredits(@Path("id") int id);

    @GET("tv/{id}/credits?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<CreditRes> getTVShowCredits(@Path("id") int id);

    @GET("movie/{id}/keywords?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<KeywordsMovieRes> getMovieKeywords(@Path("id") int id);

    @GET("tv/{id}/keywords?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<KeywordsTVRes> getTVShowKeywords(@Path("id") int id);

    @GET("person/popular?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<PeopleRes> getPopularpeople(@Query("page") int page);

    @GET("person/{person_id}?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<DetailPersonRes> getDetailPerson(@Path("person_id") int id);

    @GET("person/{person_id}/images?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<ImageRes> getPersonProfiles(@Path("person_id") int id);

    @GET("person/{person_id}/combined_credits?api_key=" + API_KEY)
    @Headers("Content-Type: application/json")
    Call<CombinedCreditsRes> getCombinedCredits(@Path("person_id") int id);
}
