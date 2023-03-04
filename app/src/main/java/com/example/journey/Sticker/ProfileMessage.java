package com.example.journey.Sticker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.journey.R;
import com.example.journey.Sticker.Models.StickerUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileMessage extends AppCompatActivity {
  private static final String TAG = "ProfileMessageActivity";

  StickerUser sample;
  private TextView logoutText;
  private Button messengerButton;
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
    email = findViewById(R.id.email_text);


    userAuthentication = FirebaseAuth.getInstance();
    fbUser = userAuthentication.getCurrentUser();
    email.setText(fbUser.getEmail());

    reference = FirebaseDatabase.getInstance().getReference();
    //reference.child("users").

    sample = new StickerUser(fbUser.getEmail());
    sample.addSticker(Constants.ANGRY);
    sample.addSticker(Constants.ANGRY);
    sample.addSticker(Constants.TIRED);
    //sample.addSticker(Constants.SHOCKED);

    reference.child("users").child(fbUser.getUid()).setValue(sample);

    stickerHistoryGrid = (GridView) findViewById(R.id.sticker_history_grid);
    StickerGridAdapter adapter = new StickerGridAdapter(this.getApplicationContext(), true);
    stickerHistoryGrid.setAdapter(adapter);


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
        //FirebaseAuth.getInstance().signOut();
        finish();
      }
    });
  }

  public void openMessengerActivity(View view) {
         Intent intent1 = new Intent(ProfileMessage.this, MessengerChatView.class);
         //intent1.putExtra("loggedInUsers",loggedInUsers);
         startActivity(intent1);


  }

}