package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    logoutText = findViewById(R.id.logout_text);
    messengerButton = findViewById(R.id.send_message);
    messengerButton = findViewById(R.id.history);
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

}