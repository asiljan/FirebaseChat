package com.alen.firebasesampleproject.data.models;

/**
 * Created by alensiljan on 31/08/16.
 */
public class UserModel {

    private String user;
    private String token;

    public UserModel(String uUid, String token) {
        this.user = uUid;
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
