package com.duc.themovie.view.fragment;

import static android.accounts.AccountManager.KEY_PASSWORD;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.duc.themovie.CommonUtils;
import com.duc.themovie.R;
import com.duc.themovie.api.res.SessionRes;
import com.duc.themovie.databinding.LoginFragmentBinding;
import com.duc.themovie.viewmodel.LoginVM;

public class LoginFragment extends BaseFrg<LoginFragmentBinding, LoginVM> {
    public static final String TAG = LoginFragment.class.getName();
    public static final String KEY_USER_NAME = "KEY_USER_NAME";
    private static final String KEY_PASSWORD_ACC = "KEY_PASSWORD_ACC";

    @Override
    protected Class<LoginVM> getClassViewModel() {
        return LoginVM.class;
    }

    @Override
    protected void initView() {
        binding.btLogin.setOnClickListener(this);
        binding.tvCreateUser.setOnClickListener(this);
        binding.cbRememberMe.setChecked(true);
        if (CommonUtils.getInstance().getPref(KEY_USER_NAME) != null && CommonUtils.getInstance().getPref(KEY_PASSWORD) != null) {
            binding.edtUsername.setText(CommonUtils.getInstance().getPref(KEY_USER_NAME));
            binding.edtPassword.setText(CommonUtils.getInstance().getPref(KEY_PASSWORD_ACC));
        }
    }

    @Override
    protected void clickView(View v) {
        if (v.getId() == R.id.btLogin) {
            String userName = binding.edtUsername.getText().toString();
            String password = binding.edtPassword.getText().toString();

            int rs = viewModel.validate(userName, password);
            if (rs == LoginVM.ERROR_PASSWORD_EMPTY) {
                notify("Password is empty!");
            } else if (rs == LoginVM.ERROR_USER_NAME_EMPTY) {
                notify("Username is empty!");
            } else {
                viewModel.getAuthen(userName, password);
            }
        } else if (v.getId() == R.id.tvCreateUser) {
            openRegisterLink();
        }
    }

    private void openRegisterLink() {
        String link = "https://www.themoviedb.org/signup";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    @Override
    protected LoginFragmentBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return LoginFragmentBinding.inflate(inflater, container, false);
    }

    @Override
    public void apiError(String key, int code, Object data) {
        if (code == 401) {
            Toast.makeText(context, "Ten danh nhap hoac mat khau khong dung", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error: " + code + ", " + data, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void apiSuccess(String key, Object data) {
        if (key.equals(LoginVM.KEY_CREATE_SESSION_ID)) {
            SessionRes res = (SessionRes) data;
            Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show();
            if (binding.cbRememberMe.isChecked()) {
                CommonUtils.getInstance().savePref(KEY_USER_NAME, binding.edtUsername.getText().toString());
                CommonUtils.getInstance().savePref(KEY_PASSWORD_ACC, binding.edtPassword.getText().toString());
            }
            NavController navigation = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navigation.navigate(R.id.action_login_to_home);
        }
        super.apiSuccess(key, data);
    }
}
