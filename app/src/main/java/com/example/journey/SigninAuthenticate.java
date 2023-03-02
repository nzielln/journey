package com.example.journey;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.journey.databinding.ActivitySigninAuthenticateBinding;

import java.util.HashMap;
import java.util.Map;

public class SigninAuthenticate extends AppCompatActivity {
  private UserViewModel logInData;
  private Map<String, String> results = new HashMap<>();

  private FirebaseAuth myAuthentication;
  private ActivitySigninAuthenticateBinding binding;
  private String email;
  private String password;
  private FragmentManager fragmentManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    fragmentManager = getSupportFragmentManager();
    binding = ActivitySigninAuthenticateBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    signInUserView();

    logInData = new ViewModelProvider(this).get(UserViewModel.class);

    if (checkIsUserSignedIn() ) {
      openMessengerActivity();
    } else {
      logInData.getShowCreateAccount().observe(this, state -> {
        if (state) {
          createNewAccountView();
        } else {
          signInUserView();
        }
      });
    }

    logInData.getPassword().observe(this, password -> {
      results.put(Constants.PASSWORD_KEY, password);
      signInUser(results.get(Constants.EMAIL_KEY), results.get(Constants.PASSWORD_KEY));
    });

    logInData.getEmail().observe(this, email -> {
      results.put(Constants.EMAIL_KEY, email); // hOW TO LISTEN TO MULTIPLE THINGS AT ONCE


    });

    myAuthentication = FirebaseAuth.getInstance();



    //FirebaseAuth.getInstance().signOut();

  }

  public Boolean checkIsUserSignedIn() {

    return false;
  }



  public void openMessengerActivity(){}

  public void signInUserView() {
    FragmentTransaction transaction  =  fragmentManager.beginTransaction();
    transaction.replace(R.id.fragment_container, SignIn.class, null).commit();

  }

  public void createNewAccountView() {
    FragmentTransaction transaction  =  fragmentManager.beginTransaction();
    transaction.replace(R.id.fragment_container, CreateAccount.class, null).commit();

  }

  public void getCredentials() {

  }

  public void signInUser(String email, String password) {
    myAuthentication.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(SigninAuthenticate.this, new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                  FirebaseUser user = myAuthentication.getCurrentUser();
                  updateInterface(user);
                } else { // If the signin fails
                  System.out.print("UH OH SOMETHING WENT WRONG :/");
                  updateInterface(null);
                }
              }

            });
  }

  public void createNewUser(String email, String password) {
    myAuthentication.signInWithEmailAndPassword(email, password) //change this
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
  }

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
      System.out.print("yaaaaa it worked");
    }

    private void reload() {
    }




}
