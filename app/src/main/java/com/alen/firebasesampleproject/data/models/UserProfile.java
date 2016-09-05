package com.alen.firebasesampleproject.data.models;

/**
 * Created by alensiljan on 01/09/16.
 */
public class UserProfile {

    private String photoUrl;
    private String userName;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
