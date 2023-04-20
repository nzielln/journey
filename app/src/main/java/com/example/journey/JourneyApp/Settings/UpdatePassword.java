package com.example.journey.JourneyApp.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {

  AlertDialog.Builder confirmMessage;
  FirebaseUser fbUser;

  EditText currPasswordEdit; // user's current password
  EditText newPasswordEdit; // user's new password
  Button updatePassword; // updates the users password
  // Buttons
  ImageView backButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_update_password);

    //fbAuth = FirebaseAuth.getInstance();
    fbUser = Database.FIREBASE_AUTH.getCurrentUser();

    // Back to Settings Activity
    backButton = (ImageView) findViewById(R.id.backToSettings);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigateUpTo(new Intent(UpdatePassword.this, Settings.class));
      }
    });

    // Alert Dialog - Confirmation Dialog for user to delete account
    confirmMessage = new AlertDialog.Builder(this);

    currPasswordEdit = (EditText) findViewById(R.id.currentPassword);
    newPasswordEdit = (EditText) findViewById(R.id.newPassword);
    updatePassword = (Button) findViewById(R.id.updatePasswordButton);
    updatePassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String currentPassword = currPasswordEdit.getText().toString().trim();
        String newPassword = currPasswordEdit.getText().toString().trim();

        if (currentPassword.isEmpty() || newPassword.isEmpty()) {
          Toast.makeText(UpdatePassword.this, "Please fill out all fields.", Toast.LENGTH_LONG).show();
        } else {
          updateUserPassword(currentPassword, newPassword);
        }
      }
    });
  }

  /**
   * The updatePassword() method updates the
   * user's password.
   */
  public void updateUserPassword(String currentPassword, String newPassword) {

    AuthCredential authUserCredential = EmailAuthProvider.getCredential(fbUser.getEmail(), currentPassword);
    fbUser.reauthenticate(authUserCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void unused) { //On Suc
        // On success, update user password
        fbUser.updatePassword(newPassword);
        Toast.makeText(UpdatePassword.this,
                "Password updated successfully!", Toast.LENGTH_LONG).show();
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        Toast.makeText(UpdatePassword.this,
                "Password failed to update", Toast.LENGTH_LONG).show();
      }
    });

  }
}