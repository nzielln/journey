package com.example.journey.Sticker;

import android.app.AlertDialog;
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

import com.example.journey.R;
import com.example.journey.Sticker.Models.StickerUser;
import com.example.journey.databinding.ActivitySigninAuthenticateBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.Objects;

public class StickerMessagingService extends Service {
    Looper looper;
    String TAG = StickerMessagingService.class.toString();
    DatabaseReference reference; //database reference
    private FirebaseAuth myAuthentication;
    FirebaseUser currentUser;

    NotificationManager notificationManager;
    Message recipientMessage;


    public StickerMessagingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread("");
        handlerThread.start();

        myAuthentication = FirebaseAuth.getInstance();
        currentUser = myAuthentication.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        String startKey = reference.push().getKey();

        notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        createStickerNotificationChannel();

        ChildEventListener childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);

                assert currentUser != null;
                assert message != null;

                if (Objects.equals(message.getRecipientID(), currentUser.getUid())) {
                    Log.i(TAG, "NEW MESSAGE RECEIEVED! ");
                    updateUserMessages(message);
                    sendNotificationToUser();
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

        reference.child(Constants.MESSAGES_DATABASE_ROOT).orderByKey().startAt(startKey).addChildEventListener(childEventListener);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateUserMessages(Message message) {
        reference.child(Constants.USERS_DATABASE_ROOT).child(currentUser.getUid()).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                StickerUser user = currentData.getValue(StickerUser.class);

                if (user != null) {
                    user.addNewMessage(message);
                    currentData.setValue(user);
                }
                Log.i(TAG, "NEW MESSAGE ADDED TO USER! ");
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                Log.e(TAG, "FAILED TO ADD NEW MESSAGE TO USER");

            }
        });

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
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(StickerMessagingService.this, stickerChannelID)
                .setContentTitle(recipientMessage.getSenderID())
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


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(notifID, notificationBuilder.build());
        //notificationManager.notify(notifID,notificationBuilder);

    }

    private void sendNotificationToUser() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}