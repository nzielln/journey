package com.example.journey.Sticker;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

    private void sendNotificationToUser() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}