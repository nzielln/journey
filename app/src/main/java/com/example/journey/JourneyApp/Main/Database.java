package com.example.journey.JourneyApp.Main;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class Database {
    static public FirebaseApp JOURNEYDB;
    static public String JOURNEYDB_TAG = "JOURNEY_DATABASE";

    static public String USERS = "users";
    static public String APPLICATION = "applications";
    static public String TASKS = "tasks";
    static public String POST_IDS = "post_ids";
    static public String LIKED_POSTS_ID = "liked_posts";
    static public String SHARED_POSTS_ID = "shared_posts";
    static public String POST_ID_TO_POST = "post_id_to_post";
    static public String USERS_ID_TO_USER = "user_id_to_user";
    static public String CHAT_ID_TO_CHAT = "chat_id_to_chat";
    static public String CHAT_ID_TO_MESSAGES = "chat_id_to_messages";
    static public String USER_ID_TO_DOCUMENT = "user_id_to_documents";
    static public String USER_ID_TO_SETTINGS = "user_id_to_settings";
    static public String INSIGHTS = "insights";

    static public void getDatabase(Context context) {
        if (JOURNEYDB != null) {
            return;
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("journey-c6761")
                .setApplicationId("1:397694645555:android:106c920c0949f8c144ba46")
                .setApiKey("AIzaSyB57v_FlAEhlZruT-E5Icwi8Kx_7DRUa7w")
                .setDatabaseUrl("https://journey-c6761-default-rtdb.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(context, options, JOURNEYDB_TAG);
        JOURNEYDB = FirebaseApp.getInstance(JOURNEYDB_TAG);

    }
}
