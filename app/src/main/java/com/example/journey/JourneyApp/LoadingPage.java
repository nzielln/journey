package com.example.journey.JourneyApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.journey.R;

public class LoadingPage extends AppCompatActivity {

  //ImageView forwardArrow;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loading_page);
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        startActivity(new Intent(LoadingPage.this, LoginPage.class));
      }
    },6000);
  }
}