package com.alen.firebasesampleproject.common;

import com.alen.firebasesampleproject.data.models.UserModel;
import com.alen.firebasesampleproject.data.models.UserProfile;
import com.alen.firebasesampleproject.di.components.AppComponent;
import com.alen.firebasesampleproject.di.components.DaggerAppComponent;
import com.alen.firebasesampleproject.di.modules.AppModule;
import com.alen.firebasesampleproject.di.modules.HostModule;
import com.alen.firebasesampleproject.di.modules.RestModule;

/**
 * Created by alensiljan on 01/09/16.
 */
public class Application extends android.app.Application {

    private static Application instance;
    private AppComponent appComponent;

    UserProfile userProfile;
    UserModel userModel;

    @Override
    public void onCreate() {
        super.onCreate();

        setInstance(this);

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getInstance()))
                .restModule(new RestModule())
                .hostModule(new HostModule())
                .build();
    }

    public static Application getInstance() {
        return instance;
    }

    public static void setInstance(Application instance) {
        Application.instance = instance;
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
