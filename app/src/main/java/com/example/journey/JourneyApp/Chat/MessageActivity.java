package com.example.journey.JourneyApp.Chat;

import static com.example.journey.JourneyApp.Dashboard.CreateNewPost.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Chat.Adapter.MessageAdapter;
import com.example.journey.JourneyApp.Chat.Model.Chat;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.JourneyApp.Settings.Notifications;
import com.example.journey.R;
import com.example.journey.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    // Constants
    // Message Notification Channel
    private static final String MESSAGE_CHANNEL_ID = "MessageChannelID";
    private static final String MESSAGE_CHANNEL_NAME = "MessageChannelName";
    private static final String MESSAGE_CHANNEL_DESCRIPTION = "MessageChannelDescription";
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    private static final int messageNotificationID = 101;

    TextView username;
    ImageView imageView;

    //RecyclerView recyclerViewy;
    EditText msg_editText;
    ImageButton sendBtn;


    FirebaseUser currentUser;
    DatabaseReference reference;
    Intent intent;

    MessageAdapter messageAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;

    String userid;
    String myid;

    ValueEventListener seenListener;

    private NotificationManager notificationManager; //For Notification from Tinashe
    private ActivityMainBinding binding; //For Notification from Tinashe


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        //Widgets
        imageView = findViewById(R.id.imageview_profile);
        username = findViewById(R.id.user_name);
        sendBtn = findViewById(R.id.btn_send);
        msg_editText = findViewById(R.id.text_send);

        //Recycler View
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        //To make Notification work
        // Notification Manager
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        //Intent
        intent = getIntent();
        userid = intent.getStringExtra("userid");

        currentUser = Database.FIREBASE_AUTH.getCurrentUser();
        myid = currentUser.getUid();

        // Retrieve user data from Firebase and set the username and profile image
        Database.DB_REFERENCE.child(Database.USERS).child(userid).addValueEventListener(new ValueEventListener() {
            //Database.DB_REFERENCE.child(Database.USERS).child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                username.setText(userModel.getFirstName() + " " + userModel.getLastName());

                if (userModel.getProfileImage() == null) {
                    imageView.setImageResource(R.drawable.person_image);
                } else {
                    Glide.with(MessageActivity.this).load(userModel.getProfileImage()).into(imageView);
                }
                readMessages(currentUser.getUid(), userid, userModel.getProfileImage());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msg_editText.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(currentUser.getUid(), userid, msg);
                } else {
                    Toast.makeText(MessageActivity.this, "Please send a non empty message", Toast.LENGTH_SHORT).show();
                }
                msg_editText.setText("");
            }
        });

//SeenMessage(userid);
    }


    private void SeenMessage(String userid) {

        reference = Database.DB_REFERENCE.child(Database.CHATS);
        ;
        seenListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat != null && chat.getReceiver() != null &&
                            chat.getReceiver().equals(currentUser.getUid()) &&
                            chat.getSender() != null && chat.getSender().equals(userid)) {
                        //if (chat.getReceiver().equals(currentUser.getUid()) && chat.getSender().equals(userid)) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("isseen", true);
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = Database.DB_REFERENCE.child(Database.CHATS);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("isseen", false);

        reference.push().setValue(hashMap);

        //Adding user to chat fragment: latest chat with contacts
        final DatabaseReference chatRef = Database.DB_REFERENCE.child(Database.CHAT_LIST)
                .child(currentUser.getUid())
                .child(userid);

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    chatRef.child("id").setValue(userid);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessages(String myid, String userid, String profileImage) {
        mChat = new ArrayList<>();
        reference = Database.DB_REFERENCE.child(Database.CHATS);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);

                    if (chat != null && chat.getReceiver() != null && chat.getReceiver().equals(myid) &&
                            chat.getSender() != null && chat.getSender().equals(userid) ||
                            chat != null && chat.getReceiver() != null && chat.getReceiver().equals(userid) &&
                                    chat.getSender() != null && chat.getSender().equals(myid)) {
                        mChat.add(chat);
                    }
                }
                //messageAdapter.notifyDataSetChanged();
                messageAdapter = new MessageAdapter(MessageActivity.this, mChat, profileImage);
                recyclerView.setAdapter(messageAdapter);
                if (mChat.size() > 0) {
                    Chat chat = mChat.get(mChat.size() - 1);
                    if (chat.getReceiver().equals(currentUser.getUid())) {
                        // Get sender name from database ( sender's ID is stored in `chat.getSender()`)
                        DatabaseReference senderRef = Database.DB_REFERENCE.child(Database.USERS).
                                child(chat.getSender()).child("firstName");
                        senderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                // Call sendMessageNotification method on the instance
                                sendMessageNotification(chat);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("readMessages", "Failed to read sender name from database", error.toException());
                            }
                        });
                    }

                }
            }
            //Moved these lines inside for loop

            //messageAdapter = new MessageAdapter(MessageActivity.this, mChat, profileImage);
            //recyclerView.setAdapter(messageAdapter);
            //if (chat.getReceiver().equals(currentUser.getUid())) {
            //    sendMessageNotification(chat);//For Notification from Tinashe
            //}


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /*private void CheckStatus(String status){
        //reference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
        DatabaseReference reference = Database.DB_REFERENCE.child(Database.USERS).child(currentUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }
     */


    @Override
    protected void onResume() {
        super.onResume();
        //CheckStatus("online");

    }

    @Override
    protected void onPause() {
        super.onPause();
      /*  if (reference != null) {
            reference.removeEventListener(seenListener);
        }
        CheckStatus("Offline");

       */
    }






    /**
     * The sendMessageNotification() method handles sending the
     * message notification.
     * Referenced the class videos/Android Studio Dolphin Essentials book
     * to help write this code.
     */

    public void sendMessageNotification(Chat chat) {

        String replyHeader = "Enter your reply here:";
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyHeader)
                .build();

        Intent messageIntent = new Intent(this, Notifications.class);
        messageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, messageIntent,
                PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder
                (android.R.drawable.ic_dialog_email, "Reply", pIntent)
                .addRemoteInput(remoteInput)
                .build();

//PendingIntent chatIntent = PendingIntent.getActivity
//(this, (int)System.currentTimeMillis(), new Intent(this, ChatFragment.class), 0);

//String messageChannelID = MESSAGE_CHANNEL_ID;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, MESSAGE_CHANNEL_ID);
            Notification messageNotification = notificationBuilder
                    .setContentTitle("New Message")
                    .setContentText("You received a new message from " + "PLACEHOLDER@email()" + "!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(android.R.drawable.ic_dialog_email)
                    .setColor(ContextCompat.getColor(this, R.color.burnt_orange))
                    .setContentIntent(pIntent)
                    .addAction(replyAction)
//.addAction(R.drawable.email_outline_icon, "Open Chat", chatIntent)
                    .setChannelId(MESSAGE_CHANNEL_ID)
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(messageNotificationID, messageNotification);
        }
    }

// ************ Handle Message notifications Here ************ //

}
    /*
    // Call readMessages when user data is retrieved
    Database.DB_REFERENCE.child(Database.USERS).child(userid).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            UserModel userModel = dataSnapshot.getValue(UserModel.class);
            username.setText(userModel.getFirstName()+ " " + userModel.getLastName());

            if (userModel.getProfileImage() == null){
                imageView.setImageResource(R.drawable.person_image);
            } else{
                Glide.with(MessageActivity.this).load(userModel.getProfileImage()).into(imageView);
            }

            readMessages(currentUser.getUid(), userid, userModel.getProfileImage());
            listenForNewMessages(currentUser.getUid(), userid, userModel.getProfileImage()); // Call listenForNewMessages here
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(MessageActivity.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
        }
    });

 */

    /*
    private void listenForNewMessages(String myid, String userid, String profileImage) {
        DatabaseReference chatsRef = Database.DB_REFERENCE.child(Database.CHATS);

        chatsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists() && snapshot.getValue() != null) {
                    Chat chat = snapshot.getValue(Chat.class);

                    // Add null check for myid and userid
                    if (currentUser.getUid() != null && userid != null && (
                            chat.getReceiver().equals(currentUser.getUid()) && chat.getSender().equals(userid) ||
                                    chat.getReceiver().equals(userid) && chat.getSender().equals(currentUser.getUid()))) {
                        mChat.add(chat);
                        messageAdapter = new MessageAdapter(MessageActivity.this, mChat, profileImage); // initialize the adapter
                        recyclerView.setAdapter(messageAdapter); // set the adapter to the recyclerview
                        messageAdapter.notifyItemInserted(mChat.size() - 1);
                        recyclerView.scrollToPosition(mChat.size() - 1);
                    }
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

 */



