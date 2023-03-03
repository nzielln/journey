package com.example.journey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;

import com.example.journey.databinding.ActivityMessengerChatViewBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessengerChatView extends AppCompatActivity {

  private static final String TAG = JRealtimeDatabase.class.getSimpleName();

  ActivityMessengerChatViewBinding bind;
  DatabaseReference myDatabase; //database reference
  private String userReceiver;

  private RadioButton user;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_messenger_chat_view);
    bind = ActivityMessengerChatViewBinding.inflate(getLayoutInflater());
    setContentView(bind.getRoot());

    user = (RadioButton) findViewById(R.id.user1);

    myDatabase = FirebaseDatabase.getInstance().getReference();

    // Update image here
    myDatabase.child("user").addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        //addSticker(snapshot);
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

  /**
   * This method adds/send a sticker to user.
   */
  public void addSticker() {

  }
}