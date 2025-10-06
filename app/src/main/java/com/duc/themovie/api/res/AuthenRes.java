package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AuthenRes implements Serializable {
    @SerializedName("success")
    public boolean success;
    @SerializedName("expire_at")

    public String expireAt;
    @SerializedName("request_token")
    public String requestToken;

    @Override
    public String toString() {
        return "AuthenRes{" +
                "success=" + success +
                ", expireAt='" + expireAt + '\'' +
                ", requestToken='" + requestToken + '\'' +
                '}';
    }
}
