package com.alen.firebasesampleproject.common;

import com.alen.firebasesampleproject.data.models.UserModel;
import com.alen.firebasesampleproject.data.models.UserProfile;
import com.alen.firebasesampleproject.di.components.AppComponent;
import com.alen.firebasesampleproject.di.components.DaggerAppComponent;
import com.alen.firebasesampleproject.di.modules.AppModule;

/**
 * Created by alensiljan on 01/09/16.
 */
public class Application extends android.app.Application {

    private static Application sInstance;
    private AppComponent mAppComponent;

    UserProfile mUserProfile;
    UserModel mUserModel;

    @Override
    public void onCreate() {
        super.onCreate();

        setInstance(this);

        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(getInstance()))
                .build();
    }

    public static Application getInstance() {
        return sInstance;
    }

    public static void setInstance(Application sInstance) {
        Application.sInstance = sInstance;
    }

    public AppComponent getmAppComponent() {
        return mAppComponent;
    }

    public UserProfile getmUserProfile() {
        return mUserProfile;
    }

    public void setmUserProfile(UserProfile mUserProfile) {
        this.mUserProfile = mUserProfile;
    }

    public UserModel getmUserModel() {
        return mUserModel;
    }

    public void setmUserModel(UserModel mUserModel) {
        this.mUserModel = mUserModel;
    }
}
