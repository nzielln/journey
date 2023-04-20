package com.example.journey.JourneyApp.Settings;

import android.content.Context;
import android.os.AsyncTask;
import android.se.omapi.Session;

import java.util.Properties;

/**
 * The HelpHelper class is a
 * helper class for the Help class. This HelpHelper class
 * enables user's to send the an email to tech support via
 * the Journey app.
 */
public class HelpHelper extends AsyncTask<Void, Void, Void> {
  Session session;
  Context context;
  String emailSupport;
  String subjectSupport;
  String messageSupport;

  /**
   * This is a constructor for the HelpHelper class.
   * @paramcontext
   * @paramemailSupport
   * @paramsubjectSupport;
   * @parammessageSupport
   */
  public HelpHelper (Context context, String emailSupport, String subjectSupport,
                     String messageSupport) {
    this.context = context;
    this.emailSupport = emailSupport;
    this.subjectSupport = subjectSupport;
    this.messageSupport = messageSupport;
  }


  @Override
  protected Void doInBackground(Void... voids) {
    Properties properties = new Properties();

    //properties.put()
    return null;
  }
}
