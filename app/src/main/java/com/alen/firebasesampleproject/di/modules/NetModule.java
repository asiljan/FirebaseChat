package com.alen.firebasesampleproject.di.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alen.firebasesampleproject.common.Application;
import com.alen.firebasesampleproject.data.api.ApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alensiljan on 06/09/16.
 */
@Module
public class NetModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Integer networkTimeout) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(networkTimeout, TimeUnit.SECONDS)
                .readTimeout(networkTimeout, TimeUnit.SECONDS)
                .build();

        return client;
    }

    @Provides
    @Singleton
    ApiService provideApiService(String mBaseUrl, OkHttpClient client) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreference(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
