package com.alen.firebasesampleproject.common;

import com.alen.firebasesampleproject.common.helpers.LogHelper;
import com.alen.firebasesampleproject.data.api.ApiService;
import com.alen.firebasesampleproject.data.api.RetrofitBuilder;
import com.alen.firebasesampleproject.data.events.UserCredentialEvent;
import com.alen.firebasesampleproject.data.models.UserModel;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by alensiljan on 01/09/16.
 */
public class RestManager {

    private static final int RESPONSE_CODE_OK = 200;
    private ApiService apiService;

    @Inject
    public RestManager(ApiService apiService) {
        this.apiService = apiService;
    }

    public void sendUserCredentials(UserModel mUserModel) {
        Call<Object> call = apiService.sendUserCredentials(mUserModel);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                LogHelper.printLogMsg("INFO onResponse? " + response.code() + ", " + response.message());
                boolean success = response.isSuccessful() && response.code() == RESPONSE_CODE_OK;
                EventBus.getDefaultInstance().post(new UserCredentialEvent(success));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                LogHelper.printLogMsg("INFO onFailure");
                EventBus.getDefaultInstance().post(new UserCredentialEvent());
            }
        });
    }
}
