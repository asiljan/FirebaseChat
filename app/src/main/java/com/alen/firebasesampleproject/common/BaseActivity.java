package com.alen.firebasesampleproject.common;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by alensiljan on 30/08/16.
 */
public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth mFirebaseAuth;

    protected void firebaseAuthInitialization() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }
}
