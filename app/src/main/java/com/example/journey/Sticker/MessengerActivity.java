package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MessengerActivity extends AppCompatActivity {
  private static final String TAG = "MessengerActivity";

  GridView stickerHistoryGrid;
  private FirebaseAuth userAuthentication;
  FirebaseUser fbUser;
  Button confirmSend;
  String recipient;
  String recipientUserID;
  Integer selectedImageId;
  ImageView selectedImage;
  TextView recipientView;
  StickerMessage message;
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
      recipientView.setText(recipient);
    }

    selectedImage = findViewById(R.id.selected_sticker);
    userAuthentication = FirebaseAuth.getInstance();
    fbUser = userAuthentication.getCurrentUser();
    databaseReference = FirebaseDatabase.getInstance().getReference();

    stickerHistoryGrid = (GridView) findViewById(R.id.sticker_history_grid);
    StickerGridAdapter adapter = new StickerGridAdapter(this.getApplicationContext());
    stickerHistoryGrid.setAdapter(adapter);

    confirmSend = findViewById(R.id.confirm_and_send);

    stickerHistoryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imageView = view.findViewById(R.id.sticker_history_image);
        selectedImageId = Constants.getStickerForPostion(position); // sticker resource id
        selectedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), selectedImageId));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/uuuu HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        message = new StickerMessage(fbUser.getUid(),recipientUserID, selectedImageId, dateFormat.format(now));

      }
    });

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
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

  }

  /**
   * The openBackToProfile() method goes back to the profile
   * activity.
   * @paramview
   */
  public void openBackToProfile(View view) {
    startActivity(new Intent(MessengerActivity.this, ProfileMessage.class));
  }

  public void sendMessage(StickerMessage message) {
    Task sendMessage = databaseReference.child(Constants.MESSAGES_DATABASE_ROOT).child(UUID.randomUUID().toString()).setValue(message);

    if (sendMessage.isSuccessful()) {
      Log.i(TAG, "SENDING MESSAGE FROM: " + message.getSenderID() + " TO: " + message.getRecipientID());
    } else if (sendMessage.isCanceled()) {
      Log.e(TAG, "FAILED TO SEND MESSAGE FROM: " + message.getSenderID() + " TO: " + message.getRecipientID());
    }
  }

}