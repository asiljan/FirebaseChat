package com.alen.firebasesampleproject.data.events;

/**
 * Created by alensiljan on 01/09/16.
 */
public class UserCredentialEvent {

    private boolean success;

    public UserCredentialEvent() {

    }

    public UserCredentialEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
