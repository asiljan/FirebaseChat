package com.alen.firebasesampleproject.mvp.listeners;

/**
 * Created by alensiljan on 19/09/16.
 */
public interface LoginListener {

    void onGoogleSignInFailed();

    void onFirebaseAuthSuccess();

    void onFirebaseAuthFailed();
}
