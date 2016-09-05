package com.alen.firebasesampleproject.data.api;

import com.alen.firebasesampleproject.BuildConfig;
import com.alen.firebasesampleproject.data.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by alensiljan on 31/08/16.
 */
public interface ApiService {

    String MESSAGING_ENDPOINT = BuildConfig.URL_ENDPOINT + "/";
    String MESSAGING_PATH = "token";

    @POST(MESSAGING_PATH)
    Call<Object> sendUserCredentials(@Body UserModel userModel);
}
