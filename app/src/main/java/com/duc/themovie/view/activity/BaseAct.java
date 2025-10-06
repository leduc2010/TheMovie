package com.duc.themovie.view.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.duc.themovie.R;
import com.duc.themovie.view.OnMainCallBack;
import com.duc.themovie.view.fragment.BaseFrg;

import java.lang.reflect.Constructor;

public abstract class BaseAct<T extends ViewBinding, V extends ViewModel> extends AppCompatActivity
        implements View.OnClickListener, OnMainCallBack {
    protected T binding;
    protected V viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        viewModel = new ViewModelProvider(this).get(initViewModel());
        binding = initViewBinding();
        setContentView(binding.getRoot());
        initView();
    }

    public void showFragment(String tag, Object data, boolean isBack) {
        try {
            Class<?> claxx = Class.forName(tag);
            Constructor<?> cons = claxx.getConstructor();
            BaseFrg<?, ?> frg = (BaseFrg<?, ?>) cons.newInstance();
            frg.callBack = this;
            frg.setData(data);
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            if (isBack) {
                trans.addToBackStack(null);
            }
            trans.replace(R.id.nav_host_fragment, frg, tag).commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Class<V> initViewModel();

    protected abstract void initView();


    protected abstract T initViewBinding();

    @Override
    public void onClick(View view) {
        clickViews();
    }

    private void clickViews() {

    }
}
