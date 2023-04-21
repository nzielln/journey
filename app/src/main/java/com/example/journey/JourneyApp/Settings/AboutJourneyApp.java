package com.example.journey.JourneyApp.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.journey.R;

public class AboutJourneyApp extends AppCompatActivity {

  ImageView backButton;
  TextView clickForHelp;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about_journey_app);

    // Back to Settings Activity
    backButton = (ImageView) findViewById(R.id.backToSettings);
    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navigateUpTo(new Intent(AboutJourneyApp.this, Settings.class));
      }
    });

    // Click here for more information
    // This takes you to the help support email
    clickForHelp = (TextView) findViewById(R.id.clickHere);
    clickForHelp.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(AboutJourneyApp.this, Help.class));
        //Help helpMethod = new Help();
        //helpMethod.sendTechSupportEmail();
      }
    });
  }
}