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
 * Created by alensiljan on 31/08/16.
 */
public class FCMHelper {

    private static final String KEY_USERNAME = "username";
    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";
    public static final int REQUEST_INVITE = 1;

    public static void sendNotificationToUser(String uid, String displayName, final String message) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl(AppConstants.FIREBASE_DB_ADDRESS);

        Map notification = new HashMap<>();
        notification.put(KEY_USERNAME, uid);
        notification.put(KEY_TITLE, displayName);
        notification.put(KEY_MESSAGE, message);

        ref.child(AppConstants.NOTIFICATIONS_CHILD).push().setValue(notification);
    }

    public static void sendInvitation(Activity context) {
        Intent intent = new AppInviteInvitation.IntentBuilder(context.getString(R.string.invitation_title))
                .setMessage(context.getString(R.string.invitation_msg))
                .setCallToActionText(context.getString(R.string.invitation_cta))
                .build();

        context.startActivityForResult(intent, REQUEST_INVITE);
    }
}
