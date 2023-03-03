package com.example.journey;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JRealtimeDatabase extends AppCompatActivity {

  private static final String TAG = JRealtimeDatabase.class.getSimpleName();

  private DatabaseReference myDatabase;
  private TextView user1;
  private TextView image_user1;

  private TextView user2;
  private TextView image_user2;

  private ImageView sendImage;
  private int imageCounter = 0;

  @SuppressLint("RestrictedApi")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_messenger);


    // myDatabase connects with Firebase
    myDatabase = FirebaseDatabase.getInstance().getReference();


    // This updates the images "stickers"
    myDatabase.child("user").addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        showNumberOfImages(snapshot);
        Log.e(TAG, "another child added, AKA another image added" + snapshot.getValue().toString());
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
}
