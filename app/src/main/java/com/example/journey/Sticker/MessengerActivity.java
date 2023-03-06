package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.journey.R;
import com.example.journey.Sticker.Models.StickerUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class MessengerActivity extends AppCompatActivity {
  private static final String TAG = "MessengerActivity";

  GridView stickerHistoryGrid;
  private FirebaseAuth userAuthentication;
  FirebaseUser fbUser;
  FirebaseUser currentUser;
  Button confirmSend;
  String recipient;
  String recipientUserID;
  Integer selectedImageId;
  ImageView selectedImage;
  TextView recipientView;
  TextView stickerLabel;
  Message message;
  DatabaseReference databaseReference;
  ArrayList<String> loggedInUsers;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_messenger);
    Bundle bundle = getIntent().getExtras();
    recipientView = findViewById(R.id.recipient_email);
    if (bundle != null) {
      recipient = bundle.getString(Constants.RECIPIENT);
      recipientView.setText(getString(R.string.recipient_email, recipient));
    }

    selectedImage = findViewById(R.id.selected_sticker);
    stickerLabel = findViewById(R.id.sticker_label);
    userAuthentication = FirebaseAuth.getInstance();
    fbUser = userAuthentication.getCurrentUser();
    databaseReference = FirebaseDatabase.getInstance().getReference();

    stickerHistoryGrid = (GridView) findViewById(R.id.sticker_history_grid);
    StickerGridAdapter adapter = new StickerGridAdapter(this.getApplicationContext());
    stickerHistoryGrid.setAdapter(adapter);

    confirmSend = findViewById(R.id.confirm_and_send);
    selectedImageId = Constants.ANGRY;
    selectedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), selectedImageId));
    stickerLabel.setText(Constants.STICKER_ANGRY);
    createMessage();


    /**
     * The setOnItemClickListener() method
     * selects the images that need to be sent.
     */
    stickerHistoryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedImageId = Constants.getStickerForPostion(position); // sticker resource id
        selectedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), selectedImageId));
        stickerLabel.setText(Constants.getStickerKey(selectedImageId).toUpperCase());
        createMessage();

      }
    });


    /**
     * The setOnClickListener() method
     * calls the sendMessage() method that sends the message.
     */
    confirmSend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendMessage(message);

      }
    });

    databaseReference.child(Constants.ID_EMAIL_DATABASE_ROOT).child(Constants.formatEmailForPath(recipient)).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        recipientUserID = snapshot.getValue(String.class);
        if (recipientUserID != null) {
          // SendStickerNotification()
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

  }

  public void createMessage(){
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/uuuu HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    message = new Message(fbUser.getEmail(), selectedImageId, dateFormat.format(now), fbUser.getUid(),recipientUserID);
  }

  /**
   * The openBackToProfile() method goes back to the profile
   * activity.
   * @paramview
   */
  public void openBackToProfile(View view) {
    startActivity(new Intent(MessengerActivity.this, ProfileMessage.class));
  }

  /**
   * The sendMessage() method handles
   * sending the message to the recipient.
   * @param message
   */
  public void sendMessage(Message message) {
    Task sendMessage = databaseReference.child(Constants.MESSAGES_DATABASE_ROOT).push().setValue(message);


    if (sendMessage.isSuccessful()) {
      Log.i(TAG, "SENDING MESSAGE FROM: " + message.getSenderID() + " TO: " + message.getRecipientID());
    } else if (sendMessage.isCanceled()) {
      Log.e(TAG, "FAILED TO SEND MESSAGE FROM: " + message.getSenderID() + " TO: " + message.getRecipientID());
    }
    updateUserStickerCount(message.getImage());
    finish();
  }

  public void updateUserStickerCount(Integer stickerId) {
    databaseReference.child(Constants.USERS_DATABASE_ROOT).child(fbUser.getUid()).runTransaction(new Transaction.Handler() {
      @NonNull
      @Override
      public Transaction.Result doTransaction(@NonNull MutableData currentData) {
        StickerUser user = currentData.getValue(StickerUser.class);

        if (user != null) {
          user.addSticker(stickerId);
          currentData.setValue(user);
        }
        Log.i(TAG, "NEW MESSAGE ADDED TO USER! ");
        return Transaction.success(currentData);
      }

      @Override
      public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
        Log.e(TAG, "FAILED TO ADD NEW MESSAGE TO USER");

      }
    });
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString("recipient", recipient);
    outState.putInt("imageID", selectedImageId);
  }

  /**
   * The onRestoreInstanceState() method restores the UI
   * so that when user's rotate the phone screen the UI runs smoothly.
   * @paramsavedInstanceState
   */
  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);

    // set recipient and selectedImageId fields
    recipient = savedInstanceState.getString(Constants.RECIPIENT);
    recipientView.setText(recipient);


    selectedImageId = savedInstanceState.getInt("imageID");

  }



}