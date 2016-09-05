package com.alen.firebasesampleproject.data.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by alensiljan on 31/08/16.
 * Handles FCM logic. InstanceID token identifies application
 * to FCM server
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String FRIENDLY_ENGAGE_TOPIC = "friendly_engage";

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        System.out.println("FCM instanceID token: " + token);

        //Once a token is generated, we subscribe to topic
//        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        /*FirebaseMessaging.getInstance()
                .subscribeToTopic("user_BBBTester");*/
    }
}
