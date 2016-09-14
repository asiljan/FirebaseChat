package com.alen.firebasesampleproject.common.util;

import android.content.Context;
import android.text.format.DateUtils;

import com.alen.firebasesampleproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public class TimeHelper {

    private static final int MESSAGE_TIME_MIN = 1;
    private static final int MESSAGE_TIME_HOUR = 60;
    private static final int MESSAGE_TIME_DAY = 1440;

    private static Context sContext;

    public static String getMessageSentTime(long timeInMillis, Context context) {
        sContext = context;
        if (timeInMillis == 0) {
            return "";
        }

        long currentTime = System.currentTimeMillis();
        long messageTimeMinutes = TimeUnit.MILLISECONDS.toMinutes(timeInMillis);
        long currentTimeMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime);
        long timePassed = (currentTimeMinutes - messageTimeMinutes);

        if (timePassed < MESSAGE_TIME_MIN) {
            return getStringFromRes(R.string.message_time_delivered_now);
        } else if (timePassed >= MESSAGE_TIME_MIN && timePassed < MESSAGE_TIME_HOUR) {
            return ("" + timePassed + getStringFromRes(R.string.message_time_delivered));
        } else if (timePassed >= MESSAGE_TIME_HOUR && timePassed < MESSAGE_TIME_DAY &&
                DateUtils.isToday(timeInMillis)) {
            return formatDate(getStringFromRes(R.string.message_time_hours), timeInMillis);
        } else {
            return formatDate(getStringFromRes(R.string.message_time_days), timeInMillis);
        }
    }

    private static String formatDate(String formatter, long timeInMillis) {
        SimpleDateFormat mFormatter = new SimpleDateFormat(formatter, Locale.getDefault());
        Date date = new Date(timeInMillis);
        return mFormatter.format(date);
    }

    private static String getStringFromRes(int resId) {
        return sContext.getString(resId);
    }
}
