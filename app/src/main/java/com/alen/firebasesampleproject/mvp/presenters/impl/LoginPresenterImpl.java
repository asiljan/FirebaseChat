package com.alen.firebasesampleproject.mvp.presenters.impl;

import android.app.Activity;
import android.content.Intent;

import com.alen.firebasesampleproject.mvp.interactors.ILoginInteractor;
import com.alen.firebasesampleproject.mvp.listeners.LoginListener;
import com.alen.firebasesampleproject.mvp.presenters.ILoginPresenter;
import com.alen.firebasesampleproject.mvp.views.ILoginView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Inject;

/**
 * Created by alensiljan on 19/09/16.
 */
public class LoginPresenterImpl implements ILoginPresenter, LoginListener {

    private final ILoginView mLoginView;
    private final ILoginInteractor mLoginInteractor;

    @Inject
    public LoginPresenterImpl(ILoginView mLoginView, ILoginInteractor mLoginInteractor) {
        this.mLoginView = mLoginView;
        this.mLoginInteractor = mLoginInteractor;
    }

    @Override
    public void signIn(GoogleApiClient googleApiClient, Activity activity) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        mLoginInteractor.processSignIn(signInIntent, activity);
    }

    @Override
    public void firebaseAuth(Intent data, int reqCode, Activity activity) {
        mLoginInteractor.processFirebaseAuth(data, reqCode, activity, this);
    }


    @Override
    public void onGoogleSignInFailed() {
        mLoginView.setGoogleSignInFailed();
    }

    @Override
    public void onFirebaseAuthSuccess() {
        mLoginView.navigateToMain();
    }

    @Override
    public void onFirebaseAuthFailed() {
        mLoginView.setAuthFailed();
    }
}
