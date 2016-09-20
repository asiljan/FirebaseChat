package com.alen.firebasesampleproject.di.components;

import com.alen.firebasesampleproject.SignInActivity;
import com.alen.firebasesampleproject.di.modules.LoginModule;
import com.alen.firebasesampleproject.di.scopes.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by alensiljan on 19/09/16.
 */
@ActivityScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {

    void inject(SignInActivity signInActivity);
}
