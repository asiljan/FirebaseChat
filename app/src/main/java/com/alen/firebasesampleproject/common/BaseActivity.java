package com.alen.firebasesampleproject.common;

import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Base class that consists FirebaseAuth object and is used to fetch FirebaseAuth instance.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public class BaseActivity extends AppCompatActivity {

    protected FirebaseAuth mFirebaseAuth;

    protected void firebaseAuthInitialization() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }
}
