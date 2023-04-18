package com.example.journey.Sticker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.journey.R;
//import com.google.firebase.database.core.view.View;


/**
 * The SendNewStickerNotification class represents
 * a notification for a new sticker received by the app user.
 */
public class SendNewStickerNotification extends AppCompatActivity {
  String email;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    createStickerNotificationChannel();

    setContentView(R.layout.activity_profile_message);
  }
  private final String notificationGroupID = "notification_Group_ID";
  private final CharSequence notificationGroupName = "Notification Group";
  /**
   * The createStickerNotificationChannel() method
   * creates a notification channel and must be called
   * before the notification is send.
   */
  public void createStickerNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      CharSequence name = "New Sticker Notification";
      String notificationDescription = "A new sticker has been sent to you!";
      int priorityLevel = NotificationManager.IMPORTANCE_DEFAULT;

      NotificationChannel notificationChannel =
              new NotificationChannel("Sticker_Channel_Notification", name, priorityLevel);
      notificationChannel.setDescription(notificationDescription);

      NotificationManager notificationManager = getSystemService((NotificationManager.class));
      notificationManager.createNotificationChannel(notificationChannel);
    }
  }

  /**
   * The sendStickerNotification() method
   * handles sending the notification.
   * @paramview
   */
  public void sendStickerNotification(View view) {
    // Build notification
    String stickerChannelID = "Sticker_Channel_Notification";
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, stickerChannelID)
            .setSmallIcon(R.drawable.happy)
            .setContentTitle("New Sticker from friend!")
            .setContentText("New Sticker Yayy!");
            /*.setLargeIcon(myBitmap)
            .setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture()
                    .bigLargeIcon(null))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(R.drawable.angry, "Test", callIntent)
             */
    //.setcon
    //.build();
    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
    notificationManagerCompat.notify(0, notificationBuilder.build());
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("email", email);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    email = savedInstanceState.getString("email");
  }

}
