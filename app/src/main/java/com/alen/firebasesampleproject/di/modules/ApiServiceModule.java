package com.alen.firebasesampleproject.di.modules;

import com.alen.firebasesampleproject.data.api.ApiService;
import com.alen.firebasesampleproject.di.scopes.UserScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by alensiljan on 06/09/16.
 */
@Module
public class ApiServiceModule {

    @Provides
    @UserScope
    ApiService provideApiServiceInterface(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
