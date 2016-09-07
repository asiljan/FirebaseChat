package com.alen.firebasesampleproject.di.modules;

import com.alen.firebasesampleproject.common.RestManager;
import com.alen.firebasesampleproject.data.api.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alensiljan on 07/09/16.
 */
@Module
public class RestModule {

    @Provides
    @Singleton
    RestManager provideRestManager(ApiService apiService) {
        return new RestManager(apiService);
    }
}
