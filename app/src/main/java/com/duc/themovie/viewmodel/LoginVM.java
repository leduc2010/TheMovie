package com.duc.themovie.viewmodel;


import android.util.Log;

import com.duc.themovie.CommonUtils;
import com.duc.themovie.api.request.AccountReq;
import com.duc.themovie.api.request.RequestTokenReq;
import com.duc.themovie.api.res.AuthenRes;
import com.duc.themovie.api.res.SessionRes;

public class LoginVM extends BaseViewModel {
    public static final int RS_SUCCESS = 200;
    public static final int ERROR_USER_NAME_EMPTY = 1001;
    public static final int ERROR_PASSWORD_EMPTY = 1002;
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String TAG = LoginVM.class.getName();
    public static final String KEY_API_AUTHEN = "KEY_API_AUTHEN";
    public static final String KEY_SESSION_ID = "KEY_SESSION_ID";
    public static final String KEY_LOGIN_WITH_SESSION = "KEY_LOGIN_WITH_SESSION";
    public static final String KEY_CREATE_SESSION_ID = "KEY_CREATE_SESSION_ID";
    private static final String KEY_REQ_TOKEN = "KEY_REQ_TOKEN";
    private String username, password;

    public void getAuthen(String username, String password) {
        Log.i(TAG, "getAuthen:" + username + ", " + password);
        this.username = username;
        this.password = password;
        getAPI().getAuthen().enqueue(initHandleResponse(KEY_API_AUTHEN));
    }

    private void loginWithSession(String requestToken) {
        getAPI().createSession(new AccountReq(username, password, requestToken)).enqueue(initHandleResponse(KEY_LOGIN_WITH_SESSION));
    }

    private void createNewSession(String requestToken) {
        getAPI().createSessionID(new RequestTokenReq(requestToken)).enqueue(initHandleResponse(KEY_CREATE_SESSION_ID));
    }

    public int validate(String userName, String password) {
        if (userName == null || userName.isEmpty()) {
            return ERROR_USER_NAME_EMPTY;
        }
        if (password == null || password.isEmpty()) {
            return ERROR_PASSWORD_EMPTY;
        }
        return RS_SUCCESS;
    }

    @Override
    protected void handleSuccess(String key, Object body) {
        Log.i(TAG, "handleSuccess:" + key);
        Log.i(TAG, "handleSuccess:" + body);
        if (key.equals(KEY_API_AUTHEN)) {
            AuthenRes res = (AuthenRes) body;
            if (res.requestToken == null || res.requestToken.isEmpty()) {
                handleFail(key, 999, null);
                return;
            }
            loginWithSession(res.requestToken);
        } else if (key.equals(KEY_LOGIN_WITH_SESSION)) {
            AuthenRes res = (AuthenRes) body;
            CommonUtils.getInstance().savePref(KEY_REQ_TOKEN, res.requestToken);
            createNewSession(res.requestToken);
        } else {
            SessionRes res = (SessionRes) body;
            CommonUtils.getInstance().savePref(KEY_SESSION_ID, res.sessionId);
            super.handleSuccess(key, body);
        }
    }
}

