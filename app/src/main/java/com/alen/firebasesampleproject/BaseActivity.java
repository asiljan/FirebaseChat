package com.alen.firebasesampleproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by alensiljan on 30/08/16.
 */
public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void firebaseAuthInitialization() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }
}
