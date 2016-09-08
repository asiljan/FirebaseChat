package com.alen.firebasesampleproject.common.util;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by alensiljan on 08/09/16.
 */
public class TimeHelper {

    public static String getMessageSentTime(long timeInMillis) {
        String messageSentTime;
        if (timeInMillis == 0) {
            return "";
        }

        long currentTime = System.currentTimeMillis();
        long messageTimeMinutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis);
        long currentTimeMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime);

        if ((currentTimeMinutes - messageTimeMinutes) <= 1) {
            return "Just now";
        } else if ((currentTimeMinutes - messageTimeMinutes) < 60) {
            messageSentTime = "" + (currentTimeMinutes - messageTimeMinutes) + "min";
            return messageSentTime;
        } else {
            return String.format(Locale.getDefault(), "%02d:%02dh", TimeUnit.MILLISECONDS.toHours(timeInMillis),
                    TimeUnit.MILLISECONDS.toMinutes(timeInMillis) -
                            TimeUnit.MINUTES.toMinutes(TimeUnit.MILLISECONDS.toHours(timeInMillis)));
        }
    }
}
