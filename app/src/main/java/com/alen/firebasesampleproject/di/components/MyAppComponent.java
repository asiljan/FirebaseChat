package com.alen.firebasesampleproject.di.components;

import com.alen.firebasesampleproject.common.Application;
import com.alen.firebasesampleproject.di.modules.ApiServiceModule;
import com.alen.firebasesampleproject.di.modules.AppModule;
import com.alen.firebasesampleproject.di.modules.NetModule;
import com.alen.firebasesampleproject.messaging.MessageFragment;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by alensiljan on 06/09/16.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class, ApiServiceModule.class})
public interface MyAppComponent {
    //exported for child components
    Application application();
    Retrofit retrofit();
    void inject(MessageFragment fragment);
}
