package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journey.R;
import com.example.journey.StickerMessage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MessengerActivity extends AppCompatActivity {
  private static final String TAG = "MessengerActivity";

  GridView stickerHistoryGrid;
  private FirebaseAuth userAuthentication;
  FirebaseUser fbUser;
  Button confirmSend;
  String recipient;
  Integer selectedImageId;
  ImageView selectedImage;
  TextView recipientView;

  DatabaseReference databaseReference;
  ArrayList<String> loggedInUsers;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_messenger);
    Bundle bundle = getIntent().getExtras();
    recipientView = findViewById(R.id.recipient_email);
    if (bundle != null) {
      recipientView.setText(bundle.getString(Constants.RECIPIENT));
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
      }
    });

  }

  /**
   * The openBackToProfile() method goes back to the profile
   * activity.
   *
   * @paramview
   */
  public void openBackToProfile(View view) {
    startActivity(new Intent(MessengerActivity.this, ProfileMessage.class));
  }
/*
  public void sendMessage(String message) {

    String messageId= UUID.randomUUID().toString();
    MessageModel messageModel = new MessageModel(messageId, FirebaseAuth.getInstance().getUid(), message);

    messageAdapter.add(messageModel);
    databaseReferenceSender
            .child(messageId)
            .setValue(messageModel);

    databaseReferenceReceiver
            .child(messageId)
            .setValue(messageModel);

    // Show a toast indicating that the message was sent
    Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();

  }
  */

  public void sendMessage() {
    String message = String.valueOf(selectedImageId);
    recipient = recipientView.getText().toString();
    String sender = fbUser.getEmail();

    // Create a new message object with the sender, recipient, and message text
    StickerMessage newMessage = new StickerMessage(sender, recipient, message);

    // Get a reference to the messages node in the database
    DatabaseReference messagesRef = databaseReference.child("messages");

    // Push the new message to the database
    messagesRef.push().setValue(newMessage);

    // Show a toast indicating that the message was sent
    Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
}

}