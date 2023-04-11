package com.example.journey.JourneyApp.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.journey.JourneyApp.Login.LoginPage;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.JourneyMain;
import com.example.journey.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {
  private static final String TAG = Settings.class.toGenericString();

  GoogleSignInClient googleSignInClient;
  FirebaseAuth fbAuth;
  RelativeLayout signOutRelLay;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    fbAuth = FirebaseAuth.getInstance();

    signOutRelLay = (RelativeLayout) findViewById(R.id.signOutRL);

    GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Database.CLIENT_ID)
            .requestEmail()
            .build();

    googleSignInClient = GoogleSignIn.getClient(Settings.this, options);
  }


  /**
   * The onOptionsItemSelected() method signs a user
   * out of the app account.
   */
  //@Override
  public void onSignOut(View view) {
    Database.FIREBASE_AUTH.signOut();
    FirebaseAuth.getInstance().signOut();

    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        Log.i(TAG, "SIGNED OUT GOOGLE");
        startActivity(new Intent(Settings.this, LoginPage.class));
      }
    });

    finish();
  }

  /*@Override
  public void onBackPressed() {
    super.onBackPressed();
    finishAffinity();
  }*/


}