package com.alen.firebasesampleproject.common;

/**
 * Created by alensiljan on 01/09/16.
 */

import static org.greenrobot.eventbus.EventBus.getDefault;

public class EventBus {

    private static org.greenrobot.eventbus.EventBus uniqueInstance = getDefault();

    private EventBus() {
    }

    public static org.greenrobot.eventbus.EventBus getDefaultInstance() {
        return uniqueInstance;
    }
}
