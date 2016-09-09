package com.alen.firebasesampleproject.data.events;

import com.alen.firebasesampleproject.data.models.Message;

/**
 * Created by alensiljan on 09/09/16.
 */
public class UserAccountInfoEvent {

    private Message message;

    public UserAccountInfoEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
