package com.alen.firebasesampleproject.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alen.firebasesampleproject.di.components.AppComponent;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Base class that consists FirebaseAuth object and is used to fetch FirebaseAuth instance.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies(Application.getInstance().getmAppComponent());
    }

    protected abstract void injectDependencies(AppComponent appComponent);
}
