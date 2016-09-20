package com.alen.firebasesampleproject.mvp.presenters;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by alensiljan on 19/09/16.
 */
public interface ILoginPresenter {
    void signIn(GoogleApiClient googleApiClient, Activity activity);

    void firebaseAuth(Intent data, int reqCode, Activity activity);
}
