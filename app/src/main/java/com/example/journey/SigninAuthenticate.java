package com.example.journey;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.journey.databinding.ActivitySigninAuthenticateBinding;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;
import android.widget.Toast;

public class SigninAuthenticate extends AppCompatActivity {
    private AuthenticationViewModel logInData;
    private final Map<String, String> results = new HashMap<>();

    private FirebaseAuth myAuthentication;
    private ActivitySigninAuthenticateBinding binding;
    private FragmentManager fragmentManager;
    private static final String TAG = "SigninAuthenticate";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        myAuthentication = FirebaseAuth.getInstance();
        binding = ActivitySigninAuthenticateBinding.inflate(getLayoutInflater());
        logInData = new ViewModelProvider(this).get(AuthenticationViewModel.class);

        setContentView(binding.getRoot());

        if (checkIsUserSignedIn()) {
            openProfileActivity();
        } else {
            logInData.getShowCreateAccount().observe(this, state -> {
                if (state) {
                    createNewAccountView();
                } else {
                    signInUserView();
                }
            });
        }

        getCredentials();
        authenticateUser();
    }

    public Boolean checkIsUserSignedIn() {
        FirebaseUser user = myAuthentication.getCurrentUser();
        return user != null;
    }

    public void signInUserView() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, SignIn.class, null).commit();

    }

    public void createNewAccountView() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, CreateAccount.class, null).commit();

    }

    public void getCredentials() {
        logInData.getEmail().observe(this, email -> {
            results.put(Constants.EMAIL_KEY, email);
        });

        logInData.getPassword().observe(this, password -> {
            results.put(Constants.PASSWORD_KEY, password);
            authenticateUser();
        });
    }

    public void authenticateUser() {

        logInData.getShouldCreateNewAccount().observe(this, shouldCreateNewAccount -> {
            if (shouldCreateNewAccount != null) {
                String email = results.get(Constants.EMAIL_KEY);
                String password = results.get(Constants.PASSWORD_KEY);
                if (shouldCreateNewAccount) {
                    createNewUser(email, password);
                } else {
                    signInUser(email, password);
                }
            }

        });
    }

    public void signInUser(String email, String password) {
        myAuthentication.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(SigninAuthenticate.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "SUCCESSFULLY SIGNED IN USER");
                            FirebaseUser user = myAuthentication.getCurrentUser();

                            reloadViewWithUser(user);
                        } else {
                            Log.e(TAG, "ERROR SIGNING IN", task.getException());
                            showErrorSigningInToast();
                            reloadViewWithUser(null);
                        }
                    }

                });
    }

    public void createNewUser(String email, String password) {
        myAuthentication.createUserWithEmailAndPassword(email, password) //change this
                .addOnCompleteListener(SigninAuthenticate.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "SUCCESSFULLY CREATED NEW USER");

                            FirebaseUser user = myAuthentication.getCurrentUser();
                            reloadViewWithUser(user);
                        } else {
                            Log.e(TAG, "FAILED TO CREATE NEW USER", task.getException());
                            showErrorCreatingAccountToast();
                            reloadViewWithUser(null);
                        }
                    }
                });
    }

    private void reloadViewWithUser(@Nullable FirebaseUser user) {
        if (user != null) {
            openProfileActivity();
        }
    }

    public void openProfileActivity() {
        startActivity(new Intent(SigninAuthenticate.this, ProfileMessage.class));
    }

    public void showErrorSigningInToast() {
        Toast.makeText(SigninAuthenticate.this, Constants.ERROR_SIGNING_IN_MESSAGE,
                Toast.LENGTH_SHORT).show();
    }

    public void showErrorCreatingAccountToast() {
        Toast.makeText(SigninAuthenticate.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE,
                Toast.LENGTH_SHORT).show();
    }
}
