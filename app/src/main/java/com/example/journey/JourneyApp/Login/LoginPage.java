package com.example.journey.JourneyApp.Login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Main.JourneyMain;
import com.example.journey.JourneyApp.Signup.SignUp;
import com.example.journey.R;
import com.example.journey.Sticker.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

public class LoginPage extends AppCompatActivity {
  private static final String TAG = LoginPage.class.toGenericString();

  GoogleSignInClient googleSignInClient;
  ActivityResultLauncher<Intent> googleIntentLauncher;
  Button loginButton;
  Button signInWithGoogle;
  MaterialButton showHidePassword;
  EditText email;
  EditText password;

  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login_page);
    setUpDatabase();

    setUpGoogleAuth();

    if (userSignedIn()) {
      proceedToDashboarForUser(Database.FIREBASE_AUTH.getCurrentUser());
    }

    loginButton = findViewById(R.id.buttonLogin);
    signInWithGoogle = findViewById(R.id.sign_up_with_google);
    email = findViewById(R.id.user_email);
    password = findViewById(R.id.user_password);
    showHidePassword = findViewById(R.id.show_password);
    password.setTransformationMethod(PasswordTransformationMethod.getInstance());

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String emailInput = String.valueOf(email.getText());
        String passwordInput = String.valueOf(password.getText());
        signInUser(emailInput, passwordInput);
      }
    });

    signInWithGoogle.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        signInWithGoogle();
      }
    });

    showHidePassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
          showHidePassword.setIcon(ContextCompat.getDrawable(LoginPage.this, R.drawable.small_eye));
          password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
          showHidePassword.setIcon(ContextCompat.getDrawable(LoginPage.this, R.drawable.small_eye_closed));
          password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
      }
    });
  }

  public void setUpDatabase() {
    Database.getDatabase(this);
  }

  public Boolean userSignedIn() {
    return Database.FIREBASE_AUTH.getCurrentUser() != null;
  }

  public void openSignUpActivity(View view) {
    startActivity(new Intent(this, SignUp.class));
  }

  void signInUser(String email, String password) {
    Database.FIREBASE_AUTH.signInWithEmailAndPassword(email, password) //change this
            .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  Log.i(TAG, "SUCCESSFULLY SIGNED IN USER");

                  FirebaseUser user = Database.FIREBASE_AUTH.getCurrentUser();
                  assert user != null;
                  proceedToDashboarForUser(user);
                } else {
                  Log.e(TAG, "FAILED TO SIGN IN USER", task.getException());
                  Helper.showToast(LoginPage.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE);
                  reloadView();
                }
              }
            });
  }

  public void setUpGoogleAuth() {
    GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Database.CLIENT_ID)
            .requestEmail()
            .build();

    googleSignInClient = GoogleSignIn.getClient(LoginPage.this, options);
    googleIntentLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
              if (result == null) {
                Helper.showToast(LoginPage.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE);
                return;
              }

              if (result.getResultCode() == Activity.RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                if (task.isSuccessful()) {
                  try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    completeGoogleSignUp(account);
                  } catch (ApiException error) {
                    error.printStackTrace();
                    Log.e(TAG, "FAILED TO AUTHENTICATE USING GOOGLE - APIEXCEPTION");

                    Helper.showToast(LoginPage.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE + " \nERROR MESSAGE: " +  error.getMessage());
                  }

                } else {
                  Log.e(TAG, "FAILED TO AUTHENTICATE USING GOOGLE - GOOGLE ACCOUNT TTASK FAILED");

                  Helper.showToast(LoginPage.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE);
                }
              }
            }
    );
  }

  @Override
  protected void onResume() {
    super.onResume();
    reloadView();
  }

  @Override
  protected void onPause() {
    super.onPause();
    reloadView();
  }

  private void completeGoogleSignUp(GoogleSignInAccount account) {
    Log.i(TAG, "");
    AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
    Database.FIREBASE_AUTH.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
          Log.e(TAG, "AUTHENTICATE USING GOOGLE SUCCESSFUL");
          reloadView();
          proceedToDashboarForUser(Database.FIREBASE_AUTH.getCurrentUser());
        } else {
          reloadView();
          Log.e(TAG, "FAILED TO AUTHENTICATE USING GOOGLE");
          Helper.showToast(LoginPage.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE + " \nCREDENTIAL TASK FAILED: " + task.getException().getMessage());
        }
      }
    });
  }

  void reloadView() {
    email.getText().clear();
    password.getText().clear();

    email.clearFocus();
    password.clearFocus();
  }

  void proceedToDashboarForUser(FirebaseUser user) {
    Log.i(TAG, "PROCEED TO DASHBOARD");
    startActivity(new Intent(this, JourneyMain.class));
  }

  void signInWithGoogle() {
    googleIntentLauncher.launch(new Intent(googleSignInClient.getSignInIntent()));

  }

}