package com.alen.firebasesampleproject.common;

import android.content.Context;

import com.alen.firebasesampleproject.data.api.ApiService;
import com.alen.firebasesampleproject.data.models.UserModel;
import com.alen.firebasesampleproject.data.models.UserProfile;
import com.alen.firebasesampleproject.di.components.ApiServiceComponent;
import com.alen.firebasesampleproject.di.components.DaggerApiServiceComponent;
import com.alen.firebasesampleproject.di.components.DaggerMyAppComponent;
import com.alen.firebasesampleproject.di.components.MyAppComponent;
import com.alen.firebasesampleproject.di.modules.ApiServiceModule;
import com.alen.firebasesampleproject.di.modules.AppModule;
import com.alen.firebasesampleproject.di.modules.NetModule;

/**
 * Created by alensiljan on 01/09/16.
 */
public class Application extends android.app.Application {

    private MyAppComponent appComponent;
    private ApiServiceComponent apiServiceComponent;
    UserProfile userProfile;
    UserModel userModel;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerMyAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(ApiService.MESSAGING_ENDPOINT))
                .build();

        apiServiceComponent = DaggerApiServiceComponent.builder()
                .myAppComponent(appComponent)
                .apiServiceModule(new ApiServiceModule())
                .build();
    }

    public static MyAppComponent getAppComponent(Context context) {
        return ((Application) context.getApplicationContext()).appComponent;
    }

    public static ApiServiceComponent getApiServiceComponent(Context context) {
        return ((Application) context.getApplicationContext()).apiServiceComponent;
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
