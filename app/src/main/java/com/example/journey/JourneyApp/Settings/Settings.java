package com.example.journey.JourneyApp.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.journey.JourneyApp.Login.LoginPage;
import com.example.journey.JourneyApp.Main.Database;
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

  private static final String NOTIFICATION_GROUP_ID = "notificationGroupID";
  private static final CharSequence notificationGroupName = "Journey Notification Group";
  private static final String PAUSE_ALL_CHANNEL_ID = "pauseAllChannel";
  private static final String POST_CHANNEL_ID = "postChannel";
  private static final String MESSAGE_CHANNEL_ID = "messageChannel";

  GoogleSignInClient googleSignInClient;
  FirebaseAuth fbAuth;
  FirebaseUser fbUser;
  AlertDialog.Builder confirmMessage;
  FragmentManager fragmentManager;

  // Clickables
  ImageView backButton;
  RelativeLayout notificationRelLay;
  RelativeLayout helpRelLay;
  RelativeLayout signOutRelLay;
  RelativeLayout deleteRelLay;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    //fbAuth = FirebaseAuth.getInstance();
    fbUser = Database.FIREBASE_AUTH.getCurrentUser();

    fragmentManager = getSupportFragmentManager();

    // Calling the createNotificationChannel method
    //createJNotificationChannel();

    // Back to Profile Fragment
    backButton = (ImageView) findViewById(R.id.backToProfile);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigateUpTo(new Intent(Settings.this,ProfileFragment.class));
      }
    });

    // Notification
    notificationRelLay = (RelativeLayout) findViewById(R.id.notificationRL);
    notificationRelLay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //sendNotification(v);
        createJNotificationChannel();
      }
    });

    // Help
    helpRelLay = (RelativeLayout) findViewById(R.id.helpRL);
    helpRelLay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onGetHelp();
      }
    });

    // Sign out a user
    signOutRelLay = (RelativeLayout) findViewById(R.id.signOutRL);
    signOutRelLay.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onSignOut();
      }
    });

    // Delete User Account
    deleteRelLay = (RelativeLayout) findViewById(R.id.deactivateUserAccount);
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
   * The createJNotificationChannel() method
   * creates a notification channel and must be called
   * before the notification is send.
   */
  public void createJNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannelGroup jNotificationChannelGroup =
              new NotificationChannelGroup(NOTIFICATION_GROUP_ID, notificationGroupName);

      // Pause all
      CharSequence nameChannel1 = "Pause all";
      String descriptionChannel1 = "Temporarily pause notifications";
      int priorityChannel1 = NotificationManager.IMPORTANCE_DEFAULT; // priority level
      // Pause all Notification Channel
      NotificationChannel pauseNotificationChannel = new NotificationChannel(
              PAUSE_ALL_CHANNEL_ID, nameChannel1, priorityChannel1);
      pauseNotificationChannel.setDescription(descriptionChannel1);
      pauseNotificationChannel.setGroup(NOTIFICATION_GROUP_ID);

      // Post
      CharSequence nameChannel2 = "Posts";
      String descriptionChannel2 = "Temporarily pause post notifications";
      int priorityChannel2 = NotificationManager.IMPORTANCE_DEFAULT; // priority level
      // Post Notification Channel
      NotificationChannel postNotificationChannel = new NotificationChannel(
              POST_CHANNEL_ID, nameChannel2, priorityChannel2);
      postNotificationChannel.setDescription(descriptionChannel2);
      postNotificationChannel.setGroup(NOTIFICATION_GROUP_ID);

      // Message
      CharSequence nameChannel3 = "Messages";
      String descriptionChannel3 = "Temporarily pause messages notifications";
      int priorityChannel3 = NotificationManager.IMPORTANCE_DEFAULT; // priority level
      // Message Notification Channel
      NotificationChannel messageNotificationChannel = new NotificationChannel(
              MESSAGE_CHANNEL_ID, nameChannel3, priorityChannel3);
      messageNotificationChannel.setDescription(descriptionChannel3);
      messageNotificationChannel.setGroup(NOTIFICATION_GROUP_ID);

      //notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
      notificationManager.createNotificationChannelGroup(jNotificationChannelGroup);
      notificationManager.createNotificationChannel(postNotificationChannel);
      notificationManager.createNotificationChannel(postNotificationChannel);
      notificationManager.createNotificationChannel(messageNotificationChannel);
    }

  }



  /**
   * The sendNotification() method sends a
   * notification to the user.
   */
  public void sendNotification(View view) {
    // Build notification
    //Notification pause
  }

  /**
   * The onGetHelp() method
   * allows users to contact support for help.
   */
  public void onGetHelp() {

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