package com.alen.firebasesampleproject.data.events;

/**
 * Created by alensiljan on 01/09/16.
 */
public class UserCredentialEvent {

    private boolean mSuccess;

    public UserCredentialEvent() {

    }

    public UserCredentialEvent(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public boolean isSuccess() {
        return mSuccess;
    }
}
