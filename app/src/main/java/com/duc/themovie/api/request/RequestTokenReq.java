package com.duc.themovie.api.request;

import com.google.gson.annotations.SerializedName;

public class RequestTokenReq {
    @SerializedName("request_token")
    public String requestToken;

    public RequestTokenReq(String requestToken) {
        this.requestToken = requestToken;
    }
}
