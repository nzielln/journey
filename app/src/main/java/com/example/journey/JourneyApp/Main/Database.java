package com.example.journey.JourneyApp.Main;

import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Database {
    static public FirebaseApp JOURNEYDB;
    static public DatabaseReference DB_REFERENCE;
    static public FirebaseStorage DB_STORAGE;
    static public StorageReference DB_STORAGE_REFERENCE;
    static public FirebaseAuth FIREBASE_AUTH;
    static ActivityResultLauncher<Intent> GOOGLE_ACTIVITY_LAUNCHER;


    static public String STORAGE_PATH = "gs://journey-c6761.appspot.com";
    static public String JOURNEYDB_TAG = "JOURNEY_DATABASE";
    static public String CLIENT_ID = "397694645555-8tv0qqrf99npj92srj451sk4jktijiu3.apps.googleusercontent.com";
    static public String USERS = "users";
    static public String APPLICATIONS = "applications";
    static public String TIMELINE = "timeline";
    static public String TASKS = "tasks";
    static public String COMPLETED_TASKS = "completedTasks";
    static public String TODO_TASKS = "todoTasks";
    static public String POST_IDS = "postIDs";
    static public String LIKED_POSTS_ID = "likedPosts";
    static public String SHARED_POSTS_ID = "sharedPosts";
    static public String POST_ID_TO_POST = "postIDToPost";
    static public String CHAT_ID_TO_CHAT = "chatIDToChat";
    static public String CHAT_ID_TO_MESSAGES = "chatIDToMssages";
    static public String USER_ID_TO_DOCUMENT = "userIDToDocuments";
    static public String USER_ID_TO_SETTINGS = "userIDToSettings";
    static public String INSIGHTS = "insights";
    static public String CHATS = "Chats";
    static public String CHAT_LIST = "ChatList";

    static public String IMAGE_STORAGE = "images";
    static public String FOLLOWING = "followingList";
    static public String USER_ID = "userID";
    static public String DOCUMENTS = "documents";

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
        DB_REFERENCE = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
        FIREBASE_AUTH = FirebaseAuth.getInstance(JOURNEYDB);
        DB_STORAGE = FirebaseStorage.getInstance(JOURNEYDB, STORAGE_PATH);
        DB_STORAGE_REFERENCE = DB_STORAGE.getReference();

    }
}
