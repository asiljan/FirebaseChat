package com.alen.firebasesampleproject.di.components;

import com.alen.firebasesampleproject.di.modules.AppModule;
import com.alen.firebasesampleproject.di.modules.NetModule;
import com.alen.firebasesampleproject.di.modules.RestModule;
import com.alen.firebasesampleproject.messaging.MessageFragment;
import com.alen.firebasesampleproject.navigation.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alensiljan on 06/09/16.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class, RestModule.class})
public interface MyAppComponent {
    void inject(MainActivity activity);

    void inject(MessageFragment fragment);
}
