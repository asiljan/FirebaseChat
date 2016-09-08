package com.alen.firebasesampleproject.common.util;

import com.alen.firebasesampleproject.common.helpers.LogHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by alensiljan on 08/09/16.
 */
public class TimeHelper {

    private static final int MESSAGE_TIME_MIN = 1;
    private static final int MESSAGE_TIME_HOUR = 60;
    private static final int MESSAGE_TIME_DAY = 1440;

    public static String getMessageSentTime(long timeInMillis) {
        String messageSentTime;
        if (timeInMillis == 0) {
            return "";
        }

        Date date = new Date(timeInMillis);

        long currentTime = System.currentTimeMillis();
        long messageTimeMinutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis);
        long currentTimeMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime);

        long timePassed = (currentTimeMinutes - messageTimeMinutes);

        if (timePassed < MESSAGE_TIME_MIN) {
            return "Just now";
        } else if (timePassed >= MESSAGE_TIME_MIN && timePassed < MESSAGE_TIME_HOUR) {
            messageSentTime = "" + timePassed + " min ago";
            return messageSentTime;
        } else if (timePassed >= MESSAGE_TIME_HOUR && timePassed < MESSAGE_TIME_DAY) {

            SimpleDateFormat mFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
            messageSentTime = mFormatter.format(date);

            return messageSentTime;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd:MM:yy:HH:mm", Locale.getDefault());
            messageSentTime = formatter.format(date);
            return messageSentTime;
        }
    }
}
