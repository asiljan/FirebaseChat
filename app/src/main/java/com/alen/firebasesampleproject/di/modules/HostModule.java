package com.alen.firebasesampleproject.di.modules;

import com.alen.firebasesampleproject.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alensiljan on 13/09/16.
 */
@Module
public class HostModule {

    public static final int NETWORK_TIMEOUT = 8;
    public static final String MESSAGING_ENDPOINT = BuildConfig.URL_ENDPOINT + "/";

    @Provides
    @Singleton
    public String provideBaseUrl() {
        return MESSAGING_ENDPOINT;
    }

    @Provides
    @Singleton
    public Integer provideNetworkTimeout() {
        return NETWORK_TIMEOUT;
    }
}
