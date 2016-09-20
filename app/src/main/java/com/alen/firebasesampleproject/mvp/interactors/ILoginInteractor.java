package com.alen.firebasesampleproject.mvp.interactors;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by alensiljan on 19/09/16.
 */
public interface ILoginInteractor {
    void processSignIn(Intent intent, Activity activity);

    void processFirebaseAuth(Intent data, int requestCode, Activity activity);
}
