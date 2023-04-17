package com.example.journey.JourneyApp.Settings;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.journey.JourneyApp.Login.LoginPage;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.JourneyMain;
import com.example.journey.JourneyApp.Profile.ProfileFragment;
import com.example.journey.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The Settings class represents a Settings activity
 * where users can change their information
 * such as their name or password. Users can get help, or report
 * suspicious activity. Users can also sign out or delete their account.
 */
public class Settings extends AppCompatActivity {
  private static final String TAG = Settings.class.toGenericString();

  GoogleSignInClient googleSignInClient;
  FirebaseAuth fbAuth;
  FirebaseUser fbUser;
  RelativeLayout signOutRelLay;
  ImageView backButton;
  AlertDialog.Builder confirmMessage;
  RelativeLayout deleteRelLay;

  FragmentManager fragmentManager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    fbAuth = FirebaseAuth.getInstance();
    fbUser = Database.FIREBASE_AUTH.getCurrentUser();

    fragmentManager = getSupportFragmentManager();

    // Back to Profile Fragment
    backButton = (ImageView) findViewById(R.id.backToProfile);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigateUpTo(new Intent(Settings.this,ProfileFragment.class));
      }
    });

    // Sign out a user
    signOutRelLay = findViewById(R.id.signOutRL);
    signOutRelLay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onSignOut();
      }
    });

    // Delete User Account
    deleteRelLay = findViewById(R.id.deactivateUserAccount);
    deleteRelLay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deleteAccount();
      }
    });

    GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Database.CLIENT_ID)
            .requestEmail()
            .build();

    googleSignInClient = GoogleSignIn.getClient(Settings.this, options);

    // Alert Dialog - Confirmation Dialog for user
    confirmMessage = new AlertDialog.Builder(this);
  }

  /**
   * The updatePassword() method updates the
   * user's password.
   */
  public void updatePassword() {
    // Confirm that user wants to deactivate account
    confirmMessage.setTitle("Update Password")
            .setMessage("Are you sure you want to update your password?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                //fbUser = Database.FIREBASE_AUTH.getCurrentUser();
                fbUser.updatePassword("").addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void unused) {
                    Toast.makeText(Settings.this, "Your password had been updated", Toast.LENGTH_LONG).show();
                  }
                });
              }
            }).setNegativeButton("Cancel", null)
            .create()
            .show();


  }


  /**
   * The onOptionsItemSelected() method signs a user
   * out of the app account.
   */
  public void onSignOut() {
    Database.FIREBASE_AUTH.signOut();

    googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        Log.i(TAG, "SIGNED OUT GOOGLE");
        startActivity(new Intent(Settings.this, LoginPage.class));
      }
    });

    finish();
  }

  /**
   * The deleteAccount() method deletes
   * a users account and logs the user out.
   */
  public void deleteAccount() {
    //if (item.getItemId() == R.id.deactivateUserAccount) {
      // Confirm that user wants to deactivate account
      confirmMessage.setTitle("Deactivate Account")
              .setMessage("Are you sure you want to deactivate your account?")
              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  fbUser.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                      Toast.makeText(Settings.this, "Account Deactivated", Toast.LENGTH_LONG).show();
                      onSignOut();
                    }
                  });
                }
              }).setNegativeButton("Cancel", null)
              .create()
              .show();
    //}

  }


}