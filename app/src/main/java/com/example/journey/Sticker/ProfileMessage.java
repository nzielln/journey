package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.journey.R;
import com.example.journey.Sticker.Models.StickerUser;
import com.example.journey.StickerHistory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileMessage extends AppCompatActivity {
  private static final String TAG = "ProfileMessageActivity";

  private Button triggerNotification;
  NotificationManager notificationManager;

  private TextView logoutText;
  private Button messengerButton;
  private Button historyButton;
  TextView email;
  GridView stickerHistoryGrid;
  private  FirebaseAuth userAuthentication;
  FirebaseUser fbUser;
  DatabaseReference reference; //database reference

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_message);

    /*********Temp Code*******/
    triggerNotification = findViewById(R.id.tempNotification);
    notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
    createStickerNotificationChannel();
    /*********Temp Code*******/

    logoutText = findViewById(R.id.logout_text);
    messengerButton = findViewById(R.id.send_message);
    historyButton = findViewById(R.id.history);
    email = findViewById(R.id.email_text);

    userAuthentication = FirebaseAuth.getInstance();
    fbUser = userAuthentication.getCurrentUser();
    email.setText(fbUser.getEmail());

    reference = FirebaseDatabase.getInstance().getReference();
    StickerGridAdapter adapter = new StickerGridAdapter(this.getApplicationContext(), true);

    reference.child(Constants.USERS_DATABASE_ROOT).child(fbUser.getUid()).addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        StickerUser stickers = snapshot.getValue(StickerUser.class);
        stickerHistoryGrid = (GridView) findViewById(R.id.sticker_history_grid);
        adapter.updateUser(stickers);
        stickerHistoryGrid.setAdapter(adapter);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        System.out.println();

      }
    });

    /**
     * Trigger the notification.
     */
    triggerNotification.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        sendStickerNotification(view);
      }
    });
    messengerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openMessengerActivity(v);

      }
    });

    logoutText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        userAuthentication.signOut();
        finish();
      }
    });

    historyButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openHistoryActivity();
      }
    });
  }

  public void openMessengerActivity(View view) {
         Intent intent1 = new Intent(ProfileMessage.this, MessengerChatView.class);
         startActivity(intent1);
  }

  public void openHistoryActivity() {
    startActivity(new Intent(ProfileMessage.this, StickerHistory.class));
  }


  /**
   * The createStickerNotificationChannel() method
   * creates a notification channel and must be called
   * before the notification is send.
   * Used the class videos/Android Studio Dolphin Essentials book
   * to help write this code.
   */
  public void createStickerNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = "New Sticker Notification";
      String notificationDescription = "A new sticker has been sent to you!";
      int priorityLevel = NotificationManager.IMPORTANCE_DEFAULT;

      NotificationChannel channel =
              new NotificationChannel(getString(R.string.channel_id), name, priorityLevel);
      channel.setDescription(notificationDescription);
      channel.enableLights(true);
      channel.setLightColor(Color.RED);
      //channel.enableVibration(true);


      //NotificationManager notificationManager = getSystemService((NotificationManager.class));
      notificationManager.createNotificationChannel(channel);
    }
  }

  /**
   * The sendStickerNotification() method
   * handles sending the notification.
   * Used the class videos/Android Studio Dolphin Essentials book
   * to help write this code.
   * @paramview
   */
  public void sendStickerNotification(View view) {

    Intent intent = new Intent(this, AlertDialog.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    // Build notification
    String stickerChannelID = getString(R.string.channel_id);
    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.happy);
    int notifID = 0;
    //NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, stickerChannelID)
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ProfileMessage.this, stickerChannelID)
            .setContentTitle("New Sticker from friend!")
            .setWhen(System.currentTimeMillis())
            .setContentText("New Sticker Yayy!")
            .setSmallIcon(R.drawable.happy)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setChannelId(stickerChannelID)
            .setContentIntent(pIntent)
            .setVibrate(new long[]{100, 200, 400})
            .setLargeIcon(bitmap)
            .setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null))
            .setAutoCancel(true);

            /*.setLargeIcon(myBitmap)
            .setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture()
                    .bigLargeIcon(null))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(R.drawable.angry, "Test", callIntent)
             */
    //.setcon

    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
    notificationManagerCompat.notify(notifID, notificationBuilder.build());
    //notificationManager.notify(notifID,notificationBuilder);

  }

  /**
   * if (user receives a new sticker) {
   *
   * }
   */

}