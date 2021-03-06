package com.alen.firebasesampleproject.di.components;

import com.alen.firebasesampleproject.di.modules.AppModule;
import com.alen.firebasesampleproject.di.modules.FirebaseModule;
import com.alen.firebasesampleproject.di.modules.HostModule;
import com.alen.firebasesampleproject.di.modules.LoginModule;
import com.alen.firebasesampleproject.di.modules.NetModule;
import com.alen.firebasesampleproject.di.modules.RestModule;
import com.alen.firebasesampleproject.messaging.MessageFragment;
import com.alen.firebasesampleproject.navigation.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alensiljan on 06/09/16.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        NetModule.class,
        HostModule.class,
        RestModule.class,
        FirebaseModule.class,
})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(MessageFragment fragment);

//    FirebaseAuth firebaseAuth();

    LoginComponent newLoginComponent(LoginModule loginModule);
}
