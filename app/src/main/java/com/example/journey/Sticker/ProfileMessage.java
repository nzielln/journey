package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Objects;

public class ProfileMessage extends AppCompatActivity {
  private static final String TAG = "ProfileMessageActivity";

  StickerUser sample;
  private TextView logoutText;
  private Button messengerButton;
  TextView email;
  GridView stickerHistoryGrid;
  private  FirebaseAuth myAuthentication;
  FirebaseUser fbUser;

  DatabaseReference databaseReference;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_message);

    logoutText = findViewById(R.id.logout_text);
    messengerButton = findViewById(R.id.send_messege);

    myAuthentication = FirebaseAuth.getInstance();
    fbUser = myAuthentication.getCurrentUser();
    databaseReference = FirebaseDatabase.getInstance().getReference();

    sample = new StickerUser(fbUser.getEmail());
    sample.addSticker(Constants.ANGRY);
    sample.addSticker(Constants.ANGRY);
    sample.addSticker(Constants.TIRED);
    sample.addSticker(Constants.SHOCKED);

    databaseReference.child("users").child("sample").setValue(sample);

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
        myAuthentication.signOut();
        //FirebaseAuth.getInstance().signOut();
        finish();
      }
    });
  }

  public void openMessengerActivity(View view) {
    startActivity(new Intent(ProfileMessage.this, MessengerActivity.class));
  }

}