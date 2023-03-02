package com.example.journey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileMessage extends AppCompatActivity {
  private TextView logoutText;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_message);

    logoutText = findViewById(R.id.logout_text);

    logoutText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
      }
    });
  }

}