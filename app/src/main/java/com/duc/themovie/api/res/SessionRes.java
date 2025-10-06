package com.duc.themovie.api.res;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SessionRes implements Serializable {
    @SerializedName("success")
    public boolean success;
    @SerializedName("expire_at")
    public String expireAt;
    @SerializedName("session_id")
    public String sessionId;

    public SessionRes(boolean success, String sessionId) {
        this.success = success;
        this.sessionId = sessionId;
    }
}
