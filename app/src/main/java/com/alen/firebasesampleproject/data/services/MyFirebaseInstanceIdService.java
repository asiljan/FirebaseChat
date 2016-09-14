package com.alen.firebasesampleproject.data.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Handles FCM logic. InstanceID token identifies application
 * to FCM server.
 *
 * @author Alen Siljan <alen.siljan@gmail.com>
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String FRIENDLY_ENGAGE_TOPIC = "friendly_engage";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        System.out.println("FCM instanceID token: " + token);

    }
}
