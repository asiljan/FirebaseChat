package com.alen.firebasesampleproject.mvp.views;

/**
 * Created by alensiljan on 19/09/16.
 */
public interface ILoginView {

    void setAuthFailed();

    void setGoogleSignInFailed();

    void navigateToMain();
}
