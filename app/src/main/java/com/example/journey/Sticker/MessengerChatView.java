package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;

import com.example.journey.JRealtimeDatabase;
import com.example.journey.databinding.ActivityMessengerChatViewBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessengerChatView extends AppCompatActivity {

  private static final String TAG = JRealtimeDatabase.class.getSimpleName();

  ActivityMessengerChatViewBinding bind;
  DatabaseReference myDatabase; //database reference
  private String userReceiver;

  //private RadioButton user;

  ArrayList<String> loggedInUsers = new ArrayList<>();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_messenger_chat_view);
    bind = ActivityMessengerChatViewBinding.inflate(getLayoutInflater());
    setContentView(bind.getRoot());

    myDatabase = FirebaseDatabase.getInstance().getReference();

    // Update image here
    myDatabase.child("users").addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot == null) {
          return;
        }

        for (DataSnapshot user : dataSnapshot.getChildren()) {
          loggedInUsers.add(user.child("email").getValue().toString());
        }
        Log.d("logged in users", String.join(", ", loggedInUsers));
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