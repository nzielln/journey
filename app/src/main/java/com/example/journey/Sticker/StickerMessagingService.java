package com.example.journey.Sticker;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.journey.databinding.ActivitySigninAuthenticateBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class StickerMessagingService extends Service {
    Looper looper;
    String TAG = StickerMessagingService.class.toString();
    DatabaseReference reference; //database reference
    private FirebaseAuth myAuthentication;
    StickerAppDelegate delegate;

    public StickerMessagingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread handlerThread = new HandlerThread("");
        handlerThread.start();

        myAuthentication = FirebaseAuth.getInstance();
        FirebaseUser currentUser = myAuthentication.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        reference.child(Constants.MESSAGES_DATABASE_ROOT).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                StickerMessage message = snapshot.getValue(StickerMessage.class);

                assert currentUser != null;
                assert message != null;

                if (Objects.equals(message.getRecipientID(), currentUser.getUid())) {
                        updateUserMessages(message);
                        sendNotificationToUser();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateUserMessages(StickerMessage message) {
        Log.i(TAG, "NEW MESSAGE RECEIEVED! ");
        Log.i(TAG, "MESSAGE: " + message);
    }

    private void sendNotificationToUser() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}