package com.alen.firebasesampleproject.common;

import com.alen.firebasesampleproject.data.api.ApiService;
import com.alen.firebasesampleproject.data.events.UserCredentialEvent;
import com.alen.firebasesampleproject.data.models.UserModel;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class consists method for posting User credentials to remote server.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public class RestManager {

    private static final int RESPONSE_CODE_OK = 200;

    ApiService mApiService;

    /**
     * Class constructor is used to store ApiService reference
     *
     * @param mApiService ApiService interface
     */
    @Inject
    public RestManager(ApiService mApiService) {
        this.mApiService = mApiService;
    }

    /**
     * This method executes API call and sends UserCredentialEvent.
     *
     * @param mUserModel UserModel
     */
    public void sendUserCredentials(UserModel mUserModel) {
        Call<Object> call = mApiService.sendUserCredentials(mUserModel);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                boolean success = response.isSuccessful() && response.code() == RESPONSE_CODE_OK;
                EventBus.getDefaultInstance().post(new UserCredentialEvent(success));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                EventBus.getDefaultInstance().post(new UserCredentialEvent());
            }
        });
    }
}
