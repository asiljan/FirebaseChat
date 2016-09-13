package com.alen.firebasesampleproject.common;

import com.alen.firebasesampleproject.data.api.ApiService;
import com.alen.firebasesampleproject.data.models.UserModel;
import com.alen.firebasesampleproject.data.models.UserProfile;
import com.alen.firebasesampleproject.di.components.DaggerAppComponent;
import com.alen.firebasesampleproject.di.components.AppComponent;
import com.alen.firebasesampleproject.di.modules.AppModule;
import com.alen.firebasesampleproject.di.modules.NetModule;
import com.alen.firebasesampleproject.di.modules.RestModule;

/**
 * Created by alensiljan on 01/09/16.
 */
public class Application extends android.app.Application {

    private AppComponent appComponent;
    UserProfile userProfile;
    UserModel userModel;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(ApiService.MESSAGING_ENDPOINT))
                .restModule(new RestModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

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
