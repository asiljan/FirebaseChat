package com.alen.firebasesampleproject.common.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.alen.firebasesampleproject.R;
import com.alen.firebasesampleproject.common.AppConstants;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * This class consists only static methods for sending invitations and
 * storing push message into Firebase DB.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public class FCMHelper {

    private static final String KEY_USERNAME = "username";
    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    public static final int REQUEST_INVITE = 1;

    /**
     * This method stores message info as HashMap into Firebase DB.
     *
     * @param uid String
     * @param displayName String
     * @param message String
     */
    public static void sendNotificationToUser(String uid, String displayName, final String message) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(AppConstants.FIREBASE_DB_ADDRESS);

        Map notification = new HashMap<>();
        notification.put(KEY_USERNAME, uid);
        notification.put(KEY_TITLE, displayName);
        notification.put(KEY_MESSAGE, message);

        ref.child(AppConstants.NOTIFICATIONS_CHILD).push().setValue(notification);
    }

    /**
     * This method creates Intent for sending invitations.
     *
     * @param activity Activity
     */
    public static void sendInvitation(Activity activity) {
        Intent intent = new AppInviteInvitation.IntentBuilder(activity.getString(R.string.invitation_title))
                .setMessage(activity.getString(R.string.invitation_msg))
                .setCallToActionText(activity.getString(R.string.invitation_cta))
                .build();

        activity.startActivityForResult(intent, REQUEST_INVITE);
    }
}
