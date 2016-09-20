package com.alen.firebasesampleproject.mvp.interactors;

import android.app.Activity;
import android.content.Intent;

import com.alen.firebasesampleproject.mvp.listeners.LoginListener;

/**
 * Created by alensiljan on 19/09/16.
 */
public interface ILoginInteractor {
    void processSignIn(Intent intent, Activity activity);

    void processFirebaseAuth(Intent data, int requestCode, Activity activity, LoginListener loginListener);
}
