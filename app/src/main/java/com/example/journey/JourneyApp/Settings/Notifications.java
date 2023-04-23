package com.example.journey.JourneyApp.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import static com.example.journey.JourneyApp.Dashboard.CreateNewPost.TAG;

import androidx.core.app.RemoteInput;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.journey.JourneyApp.Chat.MessageActivity;
import com.example.journey.JourneyApp.Chat.Model.Chat;
import com.example.journey.R;
import com.example.journey.databinding.ActivityMainBinding;

public class Notifications extends AppCompatActivity {

  private ImageView backButton;
  private Button messageButton;
  private Button postButton;
  private Context context;

  private ActivityMainBinding binding;
  private NotificationManager notificationManager;

  // Constants
  // Message Notification Channel
  private static final String MESSAGE_CHANNEL_ID = "MessageChannelID";
  private static final String MESSAGE_CHANNEL_NAME = "MessageChannelName";
  private static final String MESSAGE_CHANNEL_DESCRIPTION = "MessageChannelDescription";
  private static final String KEY_TEXT_REPLY = "key_text_reply";
  private static final int messageNotificationID = 101;

  public static final int NOTIFICATION_ID_NUMBER = 0;

  // Post Notifications Channel
  private static final String POST_CHANNEL_ID = "PostChannelID";
  private static final String POST_CHANNEL_NAME = "PostChannelName";
  private static final String POST_CHANNEL_DESCRIPTION = "PostChannelDescription";
  private static final int postNotificationID = 101;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notifications);

    // Back to Settings Activity
    backButton = findViewById(R.id.backToSettings);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //navigateUpTo(new Intent(Notifications.this, Settings.class));
        Intent intent = new Intent(Notifications.this, Settings.class);
        startActivity(intent);
      }
    });

    // Notification Manager
    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    // Create Message notification channel
    createMessageNChannel(MESSAGE_CHANNEL_ID, MESSAGE_CHANNEL_NAME, MESSAGE_CHANNEL_DESCRIPTION);
    // Handle the user text input
    handleMessageInput();

    // Create Post notification channel
    createPostNChannel(POST_CHANNEL_ID, POST_CHANNEL_NAME, POST_CHANNEL_DESCRIPTION);


    messageButton = findViewById(R.id.messages);
    messageButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendMessageNotification(new Chat());//v replaced by chat

      }
    });

    postButton = findViewById(R.id.posts);
    postButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendPostNotification(v);
      }
    });

  }

  // ************ Handle Message notifications Here ************ //

  /**
   * The createMessageNChannel() method creates a notification
   * channel for message notifications and must be called before the notification is sent.
   * Referenced the class videos/Android Studio Dolphin Essentials book
   * to help write this code.
   */
  public void createMessageNChannel(String id, String name, String description) {

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

  /**
   * The handleMessageInput() method handles the
   * user's written input message in the notification section.
   * Referenced the "Android Studio Dolphin Essentials" book and the following website:
   * https://developer.android.com/develop/ui/views/notifications/build-notification#java
   */

  private void handleMessageInput() {
    Intent mInputIntent = this.getIntent();
    Bundle remoteInput = RemoteInput.getResultsFromIntent(mInputIntent);

    // if the remote user input exist
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
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
      }
      notificationManagerCompat.notify(messageNotificationID, replyReceivedNotification);
    }
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
    //Intent messageIntent = new Intent(this, MessageActivity.class);
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
  // ************ Handle Post notifications Here ************ //

  /**
   * The createPostNChannel() method creates a notification
   * channel for post notifications and must be called before the notification is sent.
   * Referenced the class videos/Android Studio Dolphin Essentials book
   * to help write this code.
   */
  public void createPostNChannel(String id, String name, String description) {

    int mPriority = NotificationManager.IMPORTANCE_DEFAULT; // importance level
    // Message Notification Channel
    NotificationChannel messageNC = new NotificationChannel(id, name, mPriority);
    messageNC.setDescription(description);
    messageNC.enableLights(true);
    messageNC.setLightColor(Color.BLUE);
    messageNC.enableVibration(true);
    messageNC.setVibrationPattern(new long[] {100, 200, 300, 400, 500, 400, 300, 200, 400});

    notificationManager.createNotificationChannel(messageNC);

  }

  /**
   * The sendPostNotification() method handles sending the
   * post notifications.
   * Referenced the class videos/Android Studio Dolphin Essentials book
   * to help write this code.
   */

  public void sendPostNotification(View view) {

    Intent messageIntent = new Intent(this, Notifications.class);
    messageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    PendingIntent pIntent = PendingIntent.getActivity(this, 0, messageIntent,
            PendingIntent.FLAG_MUTABLE);


    //PendingIntent chatIntent = PendingIntent.getActivity
    //(this, (int)System.currentTimeMillis(), new Intent(this, ChatFragment.class), 0);

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, POST_CHANNEL_ID);
      Notification postNotification = notificationBuilder
              .setContentTitle("New Post From " + "PLACEHOLDER@email()" + "!")
              //.setContentText("PLACEHOLDER@email()" + "created a post!")
              .setPriority(NotificationCompat.PRIORITY_HIGH)
              .setSmallIcon(android.R.drawable.ic_dialog_email)
              .setColor(ContextCompat.getColor(this, R.color.burnt_orange))
              .setContentIntent(pIntent)
              .setChannelId(POST_CHANNEL_ID)
              .setAutoCancel(true)
              .build();
      notificationManager.notify(postNotificationID, postNotification);
    }
  }



}