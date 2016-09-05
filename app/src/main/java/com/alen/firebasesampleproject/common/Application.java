package com.alen.firebasesampleproject.common;

import com.alen.firebasesampleproject.data.models.UserModel;
import com.alen.firebasesampleproject.data.models.UserProfile;

/**
 * Created by alensiljan on 01/09/16.
 */
public class Application extends android.app.Application {

    UserProfile userProfile;
    UserModel userModel;

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
