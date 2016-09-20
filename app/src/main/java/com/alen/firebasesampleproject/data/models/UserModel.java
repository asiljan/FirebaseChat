package com.alen.firebasesampleproject.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by alensiljan on 31/08/16.
 */
public class UserModel implements Serializable {

    @SerializedName("user")
    private String mUser;
    @SerializedName("token")
    private String mToken;

    public UserModel(String uUid, String mToken) {
        this.mUser = uUid;
        this.mToken = mToken;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String mUser) {
        this.mUser = mUser;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }
}
