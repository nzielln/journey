package com.example.journey;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.journey.databinding.ActivitySigninAuthenticateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninAuthenticate extends AppCompatActivity {

  private FirebaseAuth myAuthentication;
  //private ActivitySigninAuthenticateBinding binding;
  private String email = "sample@email.com";
  private String password = "password";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //binding = ActivitySigninAuthenticateBinding.inflate(getLayoutInflater());
    //setContentView(binding.getRoot());

    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true)
            .add(R.id.signin_fragment, SignIn.class, null).commit();
    myAuthentication = FirebaseAuth.getInstance();

    //binding.

    myAuthentication.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(SigninAuthenticate.this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  FirebaseUser user = myAuthentication.getCurrentUser();
                  updateInterface(user);
                } else { // If the signin fails
                  updateInterface(null);
                }
              }

            });

    //FirebaseAuth.getInstance().signOut();

  }

  public void signInUser() {}

  /**
   * The onStart() method checks to
   * see if the user is already signed in.
   */
  @Override
  public void onStart() {
    super.onStart();
    // Check if user signed in and update UI when necessary
    FirebaseUser user = myAuthentication.getCurrentUser();
    if (user != null) {
      reload();
    }
    //updateUI(user);
  }


    private void updateInterface(@Nullable FirebaseUser user) {
    }

    private void reload() {
    }




}
