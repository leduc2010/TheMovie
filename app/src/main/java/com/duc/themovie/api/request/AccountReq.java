package com.duc.themovie.api.request;

import com.google.gson.annotations.SerializedName;

public class AccountReq {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("request_token")
    public String requestToken;

    public AccountReq(String username, String password, String requestToken) {
        this.username = username;
        this.password = password;
        this.requestToken = requestToken;
    }
}
