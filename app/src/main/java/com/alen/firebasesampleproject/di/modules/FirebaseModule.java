package com.alen.firebasesampleproject.di.modules;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alensiljan on 19/09/16.
 */
@Module
public class FirebaseModule {

    @Singleton
    @Provides
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}
