package com.example.journey.JourneyApp.Chat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;

import com.example.journey.JourneyApp.Chat.Fragments.ChatListFragment;
import com.example.journey.JourneyApp.Chat.Model.Chat;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.R;
import com.example.journey.Sticker.Constants;
import com.example.journey.Sticker.Message;
import com.example.journey.Sticker.Models.StickerUser;
import com.example.journey.databinding.ActivityMainBinding;
import com.example.journey.databinding.ActivitySigninAuthenticateBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Objects;

import android.app.Service;

public class JourneyMessageService extends Service {

  // Constants
  // Message Notification Channel
  private static final String MESSAGE_CHANNEL_ID = "MessageChannelID";
  private static final String MESSAGE_CHANNEL_NAME = "MessageChannelName";
  private static final String MESSAGE_CHANNEL_DESCRIPTION = "MessageChannelDescription";
  private static final String KEY_TEXT_REPLY = "key_text_reply";
  private static final int messageNotificationID = 101;

  NotificationManager notificationManager;
  ActivityMainBinding binding;

  Looper looper;
  String TAG = JourneyMessageService.class.toString();
  DatabaseReference reference; //database reference private FirebaseAuth myAuthentication;
  FirebaseUser currentUser;

  Chat recipientMessage;

  public JourneyMessageService() {

  }

  @Override
  public void onCreate() {
    super.onCreate();
    HandlerThread handlerThread = new HandlerThread("");
    handlerThread.start();

    currentUser = Database.FIREBASE_AUTH.getCurrentUser();
    reference = Database.DB_REFERENCE.getRef();
    String startKey = reference.push().getKey();

    // Notification Manager
    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    // Create Message notification channel
    createMessageNChannel(MESSAGE_CHANNEL_ID, MESSAGE_CHANNEL_NAME, MESSAGE_CHANNEL_DESCRIPTION);
    // Handle the user text input
    handleMessageInput();

    ChildEventListener childEventListener = new ChildEventListener() {

      @Override
      public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        Chat message = snapshot.getValue(Chat.class);

        assert currentUser != null;
        assert message != null;

        if (Objects.equals(message.getReceiver(), currentUser.getUid())) {

          Log.i(TAG, "NEW MESSAGE RECEIEVED! ");
          //updateUserMessages(message);
          sendMessageNotification(message);

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
    };

    reference.child(Database.CHATS).orderByKey().startAt(startKey).addChildEventListener(childEventListener);
  }


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return super.onStartCommand(intent, flags, startId);
  }

  // ************ Handle Message notifications Here ************ //

  /**
   * The createMessageNChannel() method creates a notification
   * channel for message notifications and must be called before the notification is sent.
   * Referenced the class videos/Android Studio Dolphin Essentials book
   * to help write this code.
   */
  public void createMessageNChannel(String id, String name, String description) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      int mPriority = NotificationManager.IMPORTANCE_DEFAULT; // importance level
      // Message Notification Channel
      NotificationChannel messageNC = new NotificationChannel(id, name, mPriority);
      messageNC.setDescription(description);
      messageNC.enableLights(true);
      messageNC.setLightColor(Color.BLUE);
      messageNC.enableVibration(true);
      messageNC.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

      notificationManager.createNotificationChannel(messageNC);
    }


  }

  /**
   * The handleMessageInput() method handles the
   * user's written input message in the notification section.
   * Referenced the "Android Studio Dolphin Essentials" book and the following website:
   * https://developer.android.com/develop/ui/views/notifications/build-notification#java
   */

  private void handleMessageInput() {
    //Intent mInputIntent = this.ge();
    //Bundle remoteInput = RemoteInput.getResultsFromIntent(mInputIntent);

    // if the remote user input exist
    /*
    if (remoteInput != null) {
      String userInputString = remoteInput.getCharSequence(KEY_TEXT_REPLY).toString();
      // ********************** NEED TO CHANGE (textView5) ********************** //
      binding.textView5.setText(userInputString);


      // Notify user that the reply has been received
      Notification replyReceivedNotification = new Notification.Builder(this, MESSAGE_CHANNEL_ID)
              .setSmallIcon(android.R.drawable.btn_star_big_off)
              .setContentText("Reply received")
              .build();
      NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
      //notificationManagerCompat.notify(messageNotificationID, replyReceivedNotification);
    }*/
  }

  /**
   * The sendMessageNotification() method handles sending the
   * message notification.
   * Referenced the class videos/Android Studio Dolphin Essentials book
   * to help write this code.
   */

  public void sendMessageNotification(Chat chat) {

    //String replyHeader = "Enter your reply here:";
    //RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY).setLabel(replyHeader).build();

    if (chat.getReceiver() == null) {
      return;
    }
    Intent messageIntent = new Intent(this, JourneyMessageService.class);
    messageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    PendingIntent pIntent = PendingIntent.getActivity(this, 0, messageIntent,
            PendingIntent.FLAG_IMMUTABLE);

    /*
    NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder
    (android.R.drawable.ic_dialog_email, "Reply", pIntent)
    .addRemoteInput(remoteInput)
    .build();
    */

    // Create a unique channel ID for the receiver
    //String channelId = "channel_" + currentUser.getUid();

    // Create a channel for the receiver
    //NotificationChannel channel = new NotificationChannel(channelId, "Messages", NotificationManager.IMPORTANCE_HIGH);
    //notificationManager.createNotificationChannel(channel);
    NotificationCompat.Builder notificationBuilder;
    Notification messageNotification;
    NotificationManager notificationManager = getSystemService(NotificationManager.class);
    // Build the notification for the receiver's channel
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      NotificationChannel messageNC = new NotificationChannel(MESSAGE_CHANNEL_ID, MESSAGE_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
      messageNC.setDescription(MESSAGE_CHANNEL_DESCRIPTION);
      notificationManager.createNotificationChannel(messageNC);
      notificationBuilder = new NotificationCompat.Builder(this, MESSAGE_CHANNEL_ID);
    } else {
      notificationBuilder = new NotificationCompat.Builder(this);
    }
      messageNotification = notificationBuilder.setContentTitle("Message Sent!")
              //.setContentText("You received a new message!")
              .setPriority(NotificationCompat.PRIORITY_HIGH)
              .setSmallIcon(android.R.drawable.ic_dialog_email)
              .setColor(ContextCompat.getColor(this, R.color.burnt_orange))
              .setContentIntent(pIntent)
              //.addAction(replyAction)
              //.addAction(R.drawable.email_outline_icon, "Open Chat", chatIntent)
              .setChannelId(MESSAGE_CHANNEL_ID)
              .setAutoCancel(true)
              .build();
      notificationManager.notify(messageNotificationID, messageNotification);

  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

}
