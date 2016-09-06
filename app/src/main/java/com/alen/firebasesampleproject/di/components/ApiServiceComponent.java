package com.alen.firebasesampleproject.di.components;

import com.alen.firebasesampleproject.common.Application;
import com.alen.firebasesampleproject.di.modules.ApiServiceModule;
import com.alen.firebasesampleproject.di.scopes.UserScope;
import com.alen.firebasesampleproject.navigation.MainActivity;

import dagger.Component;

/**
 * Created by alensiljan on 06/09/16.
 */
@UserScope
@Component(dependencies = MyAppComponent.class, modules = ApiServiceModule.class)
public interface ApiServiceComponent {
    void inject(MainActivity activity);
}
