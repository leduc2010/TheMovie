package com.duc.themovie.view.activity;

import static com.duc.themovie.viewmodel.LoginVM.KEY_SESSION_ID;

import android.util.Log;
import android.view.View;

import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.duc.themovie.CommonUtils;
import com.duc.themovie.R;
import com.duc.themovie.databinding.ActivityMainBinding;
import com.duc.themovie.viewmodel.AllItemVM;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseAct<ActivityMainBinding, AllItemVM> {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected Class<AllItemVM> initViewModel() {
        return AllItemVM.class;
    }

    @Override
    protected void initView() {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        setSupportActionBar(binding.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            setUpNav(navController);
            setUpBottomNav(navController);
        }
    }

    private void setUpBottomNav(NavController navController) {
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
    }

    private void setUpNav(NavController navController) {
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.nav_graph);

        String sessionId = CommonUtils.getInstance().getPref(KEY_SESSION_ID);
        Log.i(TAG, "sessionId: " + sessionId);
//        if (sessionId != null && !sessionId.isEmpty()) {
            navGraph.setStartDestination(R.id.homeFragment);

//        } else {
//            navGraph.setStartDestination(R.id.loginFragment);
//        }

        navController.setGraph(navGraph);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment,
                R.id.moviesFragment,
                R.id.tvShowsFragment,
                R.id.peopleFragment
        ).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        NavigationUI.setupWithNavController(bottomNav, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.allItemFragment
                    || destination.getId() == R.id.detailItemFragment
                    || destination.getId() == R.id.detailPersonFragment
                    || destination.getId() == R.id.loginFragment) {
                binding.toolbar.setVisibility(View.GONE);
                binding.bottomNav.setVisibility(View.GONE);
            } else {
                binding.toolbar.setVisibility(View.VISIBLE);
                binding.bottomNav.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }
}