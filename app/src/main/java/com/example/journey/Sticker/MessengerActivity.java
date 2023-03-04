package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.journey.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessengerActivity extends AppCompatActivity {
  private static final String TAG = "MessengerActivity";

  GridView stickerHistoryGrid;
  private FirebaseAuth userAuthentication;
  FirebaseUser fbUser;
  Button confirmSend;
  String recipient;

  DatabaseReference databaseReference;
  ArrayList<String> loggedInUsers;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_messenger);
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      recipient = bundle.getString(Constants.RECIPIENT);
    }
    userAuthentication = FirebaseAuth.getInstance();
    fbUser = userAuthentication.getCurrentUser();
    databaseReference = FirebaseDatabase.getInstance().getReference();

    stickerHistoryGrid = (GridView) findViewById(R.id.sticker_history_grid);
    StickerGridAdapter adapter = new StickerGridAdapter(this.getApplicationContext());
    stickerHistoryGrid.setAdapter(adapter);

    confirmSend = findViewById(R.id.confirm_and_send);


  }

  /**
   * The openBackToProfile() method goes back to the profile
   * activity.
   * @paramview
   */
  public void openBackToProfile(View view) {
    startActivity(new Intent(MessengerActivity.this, ProfileMessage.class));
  }


}