package com.example.journey.JourneyApp.Signup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.journey.JourneyApp.Login.LoginPage;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Main.JourneyMain;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.example.journey.Sticker.Constants;
import com.example.journey.Sticker.SigninAuthenticate;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;
    ActivityResultLauncher<Intent> googleIntentLauncher;
    DatabaseReference databaseReference;
    Button signUpButton;
    Button signInWithGoogle;
    EditText fullName;
    EditText email;
    EditText password;
    private static final String TAG = SignUp.class.toGenericString();


    private Boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setUpDatabase();
        setUpGoogleAuth();

        signUpButton = findViewById(R.id.sign_up_button);
        signInWithGoogle = findViewById(R.id.sign_up_with_google);
        fullName = findViewById(R.id.user_full_name);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNameInput = String.valueOf(fullName.getText());
                String emailInput = String.valueOf(email.getText());
                String passwordInput = String.valueOf(password.getText());
                createNewUser(fullNameInput, emailInput, passwordInput);
            }
        });

        signInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpWithGoogle();
            }
        });

    }

    public void setUpDatabase() {
        databaseReference = FirebaseDatabase.getInstance(Database.JOURNEYDB).getReference();
        firebaseAuth = FirebaseAuth.getInstance(Database.JOURNEYDB);
    }

    public void setUpGoogleAuth() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Database.CLIENT_ID)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(SignUp.this, options);

//        googleIntentLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result == null) {
//                            Helper.showToast(SignUp.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE);
//                            return;
//                        }
//
//                        if (result.getResultCode() == Database.GOOGLE_REQUEST_CODE) {
//                            Log.i(TAG, "SUCCESSFULLY LOGGED IN USING GOOGLE");
//
//                            Intent data = result.getData();
//                            completeGoogleSignUp(data);
//
//                        }
//                    }
//                }
//        );
        googleIntentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result == null) {
                            Helper.showToast(SignUp.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE);
                            return;
                        }

                        if (result.getResultCode() == Database.GOOGLE_REQUEST_CODE) {
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                            if (task.isSuccessful()) {
                                   GoogleSignInAccount data = task.getResult();
                                   completeGoogleSignUp(data);

                            } else {
                                Helper.showToast(SignUp.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE);
                            }
                        }
                    }
                }
        );
    }

    private void completeGoogleSignUp(GoogleSignInAccount account) {
        Log.i(TAG, "");
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);



    }

    public void openLogInActivity(View view) {
        startActivity(new Intent(this, LoginPage.class));

    }

    void createNewUser(String fullname, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password) //change this
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "SUCCESSFULLY CREATED NEW USER");

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;
                            addNewUserToDatabase(user, fullname);
                            proceedToDashboarForUser(user);
                        } else {
                            Log.e(TAG, "FAILED TO CREATE NEW USER", task.getException());
                            Helper.showToast(SignUp.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE);
                            reloadView();
                        }
                    }
                });

    }

    void addNewUserToDatabase(FirebaseUser user, String fullname) {
        String[] names = fullname.split(" ");
        UserModel userModel = new UserModel(user.getUid(), user.getEmail());
        // TODO: Will need to handle edge cases - Tinashe, can you do this?
        userModel.addUserNameDetails(names[0], names[1]);

        Task<Void> taskAddUserTodB = databaseReference.child(Database.USERS).child(userModel.getUserID()).setValue(userModel);

        if (taskAddUserTodB.isSuccessful()) {
            Log.i(TAG, "SUCCESSFULLY ADDED NEW USER WITH UUID: " + user.getUid() + " TO DATABASE");
        } else if (taskAddUserTodB.isCanceled()) {
            Log.e(TAG, "FAILED TO ADD NEW USER WITH UUID: " + user.getUid() + " TO DATABASE");
        }
    }



    void reloadView() {
        fullName.getText().clear();
        email.getText().clear();
        password.getText().clear();

        fullName.clearFocus();
        email.clearFocus();
        password.clearFocus();
    }

    void proceedToDashboarForUser(FirebaseUser user) {
        startActivity(new Intent(this, JourneyMain.class));
    }

    void signUpWithGoogle() {
        googleIntentLauncher.launch(new Intent(googleSignInClient.getSignInIntent()));
    }
}