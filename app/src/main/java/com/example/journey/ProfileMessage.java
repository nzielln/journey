package com.example.journey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileMessage extends AppCompatActivity {
  private static final String TAG = "ProfileMessageActivity";

  private TextView logoutText;
  private Button messengerButton;
  private  FirebaseAuth myAuthentication;
  FirebaseUser fbUser;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_message);

    logoutText = findViewById(R.id.logout_text);
    messengerButton = findViewById(R.id.send_messege);

    myAuthentication = FirebaseAuth.getInstance();
    fbUser = myAuthentication.getCurrentUser();

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