package com.example.journey;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class GiphyWebService extends AppCompatActivity {

  private EditText urlEditText;
  private TextView titleTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_giphy_web_service);

    urlEditText = (EditText)findViewById(R.id.url_editText);
    titleTextView = (TextView) findViewById(R.id.webService);

  }

  /**
   * The WebServiceButtonHandler() method
   */

}