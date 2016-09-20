package com.alen.firebasesampleproject.common.helpers;

import android.app.Activity;
import android.content.Intent;

import com.alen.firebasesampleproject.R;
import com.google.android.gms.appinvite.AppInviteInvitation;

/**
 * This class consists only static methods for sending invitations and
 * storing push message into Firebase DB.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public class FCMHelper {

    public static final int REQUEST_INVITE = 1;

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
