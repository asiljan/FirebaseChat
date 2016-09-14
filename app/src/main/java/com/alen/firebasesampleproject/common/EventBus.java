package com.alen.firebasesampleproject.common;

/**
 * This is Singleton class and is used to return EventBus instance.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */

import static org.greenrobot.eventbus.EventBus.getDefault;

public class EventBus {

    //eagerly instantiation
    private static org.greenrobot.eventbus.EventBus mUniqueInstance = getDefault();

    /**
     * Private constructor to prevent instantiation
     */
    private EventBus() {
    }

    /**
     *
     *  @return EventBus instance
     */
    public static org.greenrobot.eventbus.EventBus getDefaultInstance() {
        return mUniqueInstance;
    }
}
