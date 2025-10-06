package com.duc.themovie.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.duc.themovie.App;
import com.duc.themovie.OnAPICallBack;
import com.duc.themovie.R;
import com.duc.themovie.Storage;
import com.duc.themovie.view.OnMainCallBack;
import com.duc.themovie.viewmodel.BaseViewModel;


public abstract class BaseFrg<B extends ViewBinding, V extends BaseViewModel> extends Fragment implements View.OnClickListener, OnAPICallBack {

    protected B binding;

    protected V viewModel;
    protected Context context;
    public OnMainCallBack callBack;
    protected Object data;
    protected NavController navController;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = initViewBinding(inflater, container);
        viewModel = new ViewModelProvider(this).get(getClassViewModel());
        viewModel.setCallBack(this);
        navController = NavHostFragment.findNavController(this);
        initView();
        return binding.getRoot();
    }

    protected abstract Class<V> getClassViewModel();

    @SuppressLint("PrivateResource")
    @Override
    public void onClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        clickView(view);
    }

    protected void clickView(View view) {
        // do nothing
    }

    protected abstract void initView();

    protected abstract B initViewBinding(LayoutInflater inflater, ViewGroup container);

    public void setData(Object data) {
        this.data = data;
    }

    public Storage getStorage() {
        return App.getInstance().getStorage();
    }

    @Override
    public void apiSuccess(String key, Object data) {
    }

    @Override
    public void apiError(String key, int code, Object data) {
        if (code == 401) {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.loginFragment);
            Toast.makeText(context, "Phien dang nhap da het han", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error: " + code + ", " + data, Toast.LENGTH_SHORT).show();
        }
    }

    protected void notify(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
