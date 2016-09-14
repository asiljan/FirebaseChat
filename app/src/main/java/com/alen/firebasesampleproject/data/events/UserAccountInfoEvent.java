package com.alen.firebasesampleproject.data.events;

import com.alen.firebasesampleproject.data.models.Message;

/**
 * Created by alensiljan on 09/09/16.
 */
public class UserAccountInfoEvent {

    private Message mMessage;

    public UserAccountInfoEvent(Message mMessage) {
        this.mMessage = mMessage;
    }

    public Message getMessage() {
        return mMessage;
    }
}
