package com.example.journey.JourneyApp.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journey.JourneyApp.Profile.ProfileFragment;
import com.example.journey.R;

public class Help extends AppCompatActivity {

   CharSequence journeyEmailAddress = "journey5520@aol.com";

  //ActivityJourneyMainBinding binding;
  //ActivityMainBinding binding;
  ImageView backButton;
  Button sendButton;
  EditText emailLine;
  EditText subjectLine;
  EditText messageLine;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //binding = ActivityMainBinding.inflate(getLayoutInflater());
    //setContentView(binding.getRoot());
    setContentView(R.layout.activity_help);

    emailLine = (EditText) findViewById(R.id.emailITSupport);
    subjectLine = (EditText) findViewById(R.id.subject);
    messageLine = (EditText) findViewById(R.id.messageHere);

    // Back to Settings Activity
    backButton = (ImageView) findViewById(R.id.backToSettings);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigateUpTo(new Intent(Help.this, Settings.class));
      }
    });

    sendButton = (Button) findViewById(R.id.sendEmail);
    sendButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        sendTechSupportEmail();
        Toast.makeText(Help.this, "Please choose preferred email browser.", Toast.LENGTH_LONG).show();
        //startActivity(new Intent(Help.this, Settings.class));
      }
    });
  }

  /**
   * The onGetHelp() method allows users
   * to contact support via email for help.
   * Referenced this website:
   * https://developer.android.com/training/basics/intents/sending
   * https://www.youtube.com/watch?v=kGkd1hrbnWY&t=493s
   */
  public void sendTechSupportEmail() {
    String supportEmail = emailLine.getText().toString().trim();
    String [] emails = supportEmail.split(",");
    String supportSubject = subjectLine.getText().toString().trim();
    String supportMessage = messageLine.getText().toString().trim();
    // If the email, or subject, or message are empty
     if (supportEmail.length() == 0 || supportSubject.length() == 0 || supportMessage.isEmpty()) {
       // Then let the user know they need to fill in all fields
       Toast.makeText(Help.this, "All fields must be filled in.", Toast.LENGTH_LONG).show();
     } if (!supportEmail.contentEquals(journeyEmailAddress)) { // If user types the wrong email
      Toast.makeText(Help.this, "Contact us at journey5520@aol.com",
              Toast.LENGTH_LONG).show();
     } else { // Else
       // Create an email intent
       Intent eIntent = new Intent(Intent.ACTION_SEND);

       eIntent.putExtra(Intent.EXTRA_EMAIL, emails);
       eIntent.putExtra(Intent.EXTRA_SUBJECT, supportSubject);
       eIntent.putExtra(Intent.EXTRA_TEXT, supportMessage);
       eIntent.setType("text/plain"); // ensures the app options appear

       // Then start the email intent activity
       startActivity(Intent.createChooser(eIntent, "Reach out to out tech support team"));
     }
  }

}