package com.example.journey.JourneyApp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.JourneyMain;
import com.example.journey.JourneyApp.Signup.SignUp;
import com.example.journey.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginPage extends AppCompatActivity {

  FirebaseAuth firebaseAuth;
  DatabaseReference databaseReference;
  Button loginButton;

  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_page);
    setUpDatabase();
    loginButton = findViewById(R.id.buttonLogin);


    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(LoginPage.this, JourneyMain.class));
      }
    });



  }

  public void setUpDatabase() {
    Database.getDatabase(this);
  }


  public void openSignUpActivity(View view) {
    startActivity(new Intent(this, SignUp.class));
  }

  void proceedToDashboard() {

  }

  void signInWithGoogle() {

  }

}