package com.alen.firebasesampleproject.di.modules;

import com.alen.firebasesampleproject.di.scopes.ActivityScope;
import com.alen.firebasesampleproject.mvp.interactors.ILoginInteractor;
import com.alen.firebasesampleproject.mvp.interactors.impl.LoginInteractorImpl;
import com.alen.firebasesampleproject.mvp.presenters.ILoginPresenter;
import com.alen.firebasesampleproject.mvp.presenters.impl.LoginPresenterImpl;
import com.alen.firebasesampleproject.mvp.views.ILoginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alensiljan on 19/09/16.
 */
@Module
public class LoginModule {

    private ILoginView loginView;

    public LoginModule(ILoginView loginView) {
        this.loginView = loginView;
    }

    @ActivityScope
    @Provides
    public ILoginView provideLoginView() {
        return loginView;
    }

    @ActivityScope
    @Provides
    public ILoginPresenter provideLoginPresenter(LoginPresenterImpl presenter) {
        return presenter;
    }

    @ActivityScope
    @Provides
    public ILoginInteractor provideLoginInteractor(LoginInteractorImpl interactor) {
        return interactor;
    }

}
