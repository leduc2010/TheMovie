    package com.duc.themovie.viewmodel;

    import static com.duc.themovie.viewmodel.LoginVM.BASE_URL;

    import android.util.Log;

    import androidx.annotation.NonNull;
    import androidx.lifecycle.ViewModel;

    import com.duc.themovie.OnAPICallBack;
    import com.duc.themovie.api.API;

    import java.util.concurrent.TimeUnit;

    import okhttp3.OkHttpClient;
    import okhttp3.ResponseBody;
    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;
    import retrofit2.converter.gson.GsonConverterFactory;

    public class BaseViewModel extends ViewModel {
        private static final String TAG = BaseViewModel.class.getName();
        protected OnAPICallBack callBack;

        public void setCallBack(OnAPICallBack callBack) {
            this.callBack = callBack;
        }

        protected final API getAPI() {
            Retrofit rf = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS).build())
                    .build();
            return rf.create(API.class);
        }

        protected <T> Callback<T> initHandleResponse(String key) {
            return new Callback<T>() {
                @Override
                public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                    if (response.code() == 200 || response.code() == 201) {
                        assert response.body() != null;
                        handleSuccess(key, response.body());
                    } else {
                        handleFail(key, response.code(), response.errorBody());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure:" + t.getMessage());
                        handleException(key, t);
                }
            };
        }

        protected void handleException(String key, Throwable t) {
            callBack.apiError(key, 999, t.getMessage());
        }

        protected void handleFail(String key, int code, ResponseBody responseBody) {
            Log.e(TAG, "handleFail:" + code);
            callBack.apiError(key, code, responseBody);
        }

        protected void handleSuccess(String key, Object body) {
            callBack.apiSuccess(key, body);
        }
    }