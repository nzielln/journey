package com.example.journey.Sticker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.journey.Framents.CreateAccount;
import com.example.journey.Framents.SignIn;
import com.example.journey.R;
import com.example.journey.Sticker.Models.StickerUser;
import com.example.journey.databinding.ActivitySigninAuthenticateBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public class SigninAuthenticate extends AppCompatActivity implements StickerAppDelegate, Parcelable {

    private FirebaseAuth myAuthentication;
    private ActivitySigninAuthenticateBinding binding;
    private FragmentManager fragmentManager;
    private static final String TAG = "SigninAuthenticate";
    DatabaseReference reference; //database reference

    private Boolean isUserSignedIn = false;

    protected SigninAuthenticate(Parcel in) {}

    public SigninAuthenticate() {}

    public static final Creator<SigninAuthenticate> CREATOR = new Creator<SigninAuthenticate>() {
        @Override
        public SigninAuthenticate createFromParcel(Parcel in) {
            return new SigninAuthenticate(in);
        }

        @Override
        public SigninAuthenticate[] newArray(int size) {
            return new SigninAuthenticate[size];
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        myAuthentication = FirebaseAuth.getInstance();
        binding = ActivitySigninAuthenticateBinding.inflate(getLayoutInflater());
        reference = FirebaseDatabase.getInstance().getReference();

        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            isUserSignedIn = savedInstanceState.getBoolean("UserSignedIn", false);
        } else {
            isUserSignedIn = checkIsUserSignedIn();
        }


        if (checkIsUserSignedIn()) {
            startMessagingService();
            openProfileActivity();
        } else {
            signInUserView();
        }

      /*  if (savedInstanceState != null) {
            Boolean isUserSignedIn = savedInstanceState.getBoolean("UserSignedIn");
            if (isUserSignedIn != null && isUserSignedIn) {
                openProfileActivity();
                return;
            }
        }*/


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("UserSignedIn", checkIsUserSignedIn());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Boolean isUserSignedIn = savedInstanceState.getBoolean("UserSignedIn");
        if (isUserSignedIn != null && isUserSignedIn) {
            openProfileActivity();
        }
    }

    public Boolean checkIsUserSignedIn() {
        FirebaseUser user = myAuthentication.getCurrentUser();
        return user != null;
    }

    public void signInUserView() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().setReorderingAllowed(true);

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.DELEGATE, this);
        SignIn signInFragment = new SignIn();
        signInFragment.setArguments(bundle);

        transaction.replace(R.id.fragment_container, signInFragment).commit();

    }

    public void createNewAccountView() {
        FragmentTransaction transaction = fragmentManager.beginTransaction().setReorderingAllowed(true);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.DELEGATE, this);
        CreateAccount createAccountFragment = new CreateAccount();
        createAccountFragment.setArguments(bundle);
        transaction.replace(R.id.fragment_container, createAccountFragment, null).commit();

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
                            addNewUserToDatabase(user);
                            reloadViewWithUser(user);
                            signInUserView();
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
            startMessagingService();
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

    public void addNewUserToDatabase(FirebaseUser user) {
        StickerUser stickerUser = new StickerUser(user.getEmail(), user.getUid());
        Task<Void> taskAddUserTodB = reference.child(Constants.USERS_DATABASE_ROOT).child(user.getUid()).setValue(stickerUser);

        Task<Void> taskAddIDEmailToDatabase = reference.child(Constants.ID_EMAIL_DATABASE_ROOT).child(Objects.requireNonNull(Constants.formatEmailForPath(user.getEmail()))).setValue(user.getUid());

        if (taskAddUserTodB.isSuccessful()) {
            Log.i(TAG, "SUCCESSFULLY ADDED NEW USER WITH UUID: " + user.getUid() + " TO DATABASE");
        } else if (taskAddUserTodB.isCanceled()) {
            Log.e(TAG, "FAILED TO ADD NEW USER WITH UUID: " + user.getUid() + " TO DATABASE");
        }

        if (taskAddIDEmailToDatabase.isSuccessful()) {
            Log.i(TAG, "SUCCESSFULLY ADDED NEW USER WITH UUID: " + user.getUid() + " TO EMAIL/UUID DATABASE");
        } else if (taskAddIDEmailToDatabase.isCanceled()) {
            Log.e(TAG, "FAILED TO ADD NEW USER WITH UUID: " + user.getUid() + " TO EMAIL/UUID DATABASE");
        }

    }

    public void startMessagingService() {
        startService(new Intent(this, StickerMessagingService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, StickerMessagingService.class));
    }

    // Parcelable Implementations
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
    }

    // Delegate Methods
    @Override
    public void signInUserWith(String email, String password) {
        Log.i(TAG, "SIGN IN USER REQUEST WAS RECEIVED FOR EMAIL: " + email + "AND PASSWORD: " + password);
        signInUser(email, password);
    }

    @Override
    public void createNewUserWith(String email, String password) {
        Log.i(TAG, "CREATE ACCOUNT REQUEST WAS RECEIVED FOR EMAIL: " + email + "AND PASSWORD: " + password);

        createNewUser(email, password);
    }

    @Override
    public void createNewAccountWasClicked() {
        Log.i(TAG, "CREATE ACCOUNT VIEW REQUEST WAS RECEIVED");

        createNewAccountView();
    }

    @Override
    public void alreadyHaveAnAccountWasClicked() {
        Log.i(TAG, "SIGN IN VIEW REQUEST WAS RECEIVED");

        signInUserView();
    }

    @Override
    public void signInWasClicked() {

    }

    @Override
    public void createAccountWasClicked() {

    }
}
