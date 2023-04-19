package com.example.journey.JourneyApp.Signup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.journey.JourneyApp.Login.LoginPage;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Main.JourneyMain;
import com.example.journey.JourneyApp.Profile.Models.ApplicationModel;
import com.example.journey.JourneyApp.Profile.Models.ApplicationStatus;
import com.example.journey.JourneyApp.Profile.Models.TaskModel;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.example.journey.Sticker.Constants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class SignUp extends AppCompatActivity {

    GoogleSignInClient googleSignInClient;
    ActivityResultLauncher<Intent> googleIntentLauncher;
    Button signUpButton;
    Button signInWithGoogle;
    EditText fullName;
    EditText email;
    MaterialButton showHidePassword;

    EditText password;
    private static final String TAG = SignUp.class.toGenericString();
    private Boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setUpGoogleAuth();

        signUpButton = findViewById(R.id.sign_up_button);
        signInWithGoogle = findViewById(R.id.sign_up_with_google);
        fullName = findViewById(R.id.user_full_name);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_password);
        showHidePassword = findViewById(R.id.show_password);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

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
                Database.FIREBASE_AUTH.signOut();
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i(TAG, "SIGNED OUT GOOGLE");
                    }
                });
                signUpWithGoogle();
            }
        });

        showHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    showHidePassword.setIcon(ContextCompat.getDrawable(SignUp.this, R.drawable.small_eye));
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    showHidePassword.setIcon(ContextCompat.getDrawable(SignUp.this, R.drawable.small_eye_closed));
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void setUpGoogleAuth() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Database.CLIENT_ID)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(SignUp.this, options);
        googleIntentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result == null) {
                        Helper.showToast(SignUp.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE);
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

                                Helper.showToast(SignUp.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE + " \nERROR MESSAGE: " +  error.getMessage());
                            }

                        } else {
                            Log.e(TAG, "FAILED TO AUTHENTICATE USING GOOGLE - GOOGLE ACCOUNT TTASK FAILED");

                            Helper.showToast(SignUp.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE);
                        }
                    }
                }
        );
    }

    private void completeGoogleSignUp(GoogleSignInAccount account) {
        Log.i(TAG, "");
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        Database.FIREBASE_AUTH.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.e(TAG, "AUTHENTICATE USING GOOGLE SUCCESSFUL");
                    String fullname = account.getDisplayName();
                    addNewUserToDatabase(Objects.requireNonNull(Database.FIREBASE_AUTH.getCurrentUser()), fullname == null ? "" : fullname);
                    proceedToDashboarForUser(Database.FIREBASE_AUTH.getCurrentUser());
                } else {
                    Log.e(TAG, "FAILED TO AUTHENTICATE USING GOOGLE");
                    Helper.showToast(SignUp.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE + " \nCREDENTIAL TASK FAILED: " + task.getException().getMessage());
                }
            }
        });
    }

    public void openLogInActivity(View view) {
        startActivity(new Intent(this, LoginPage.class));
    }

    void createNewUser(String fullname, String email, String password) {
        Database.FIREBASE_AUTH.createUserWithEmailAndPassword(email, password) //change this
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(TAG, "SUCCESSFULLY CREATED NEW USER");

                            FirebaseUser user = Database.FIREBASE_AUTH.getCurrentUser();
                            assert user != null;
                            reloadView();
                            addNewUserToDatabase(user, fullname).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        proceedToDashboarForUser(user);
                                    } else {
                                        task.getException().printStackTrace();
                                    }
                                }
                            });
                        } else {
                            Log.e(TAG, "FAILED TO CREATE NEW USER", task.getException());
                            Helper.showToast(SignUp.this, Constants.ERROR_CREATING_ACCOUNT_MESSAGE);
                            reloadView();
                        }
                    }
                });
    }

    Task<Void> addNewUserToDatabase(FirebaseUser user, String fullname) {
        String[] names = fullname.split(" ");
        UserModel userModel = new UserModel(user.getUid(), user.getEmail());
        // TODO: Will need to handle edge cases - Tinashe, can you do this?
        userModel.addUserNameDetails(names[0], names[1]);

        Task<Void> taskAddUserTodB = Database.DB_REFERENCE.child(Database.USERS).child(userModel.getUserID()).setValue(userModel);
        return taskAddUserTodB.continueWithTask(new Continuation<Void, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    task.getException().printStackTrace();
                }
                return addNewTaskModel(userModel);
            }
        });


                /*
                .addOnSuccessListener(new OnSuccessListener<Task<Void>>() {
            @Override
            public void onSuccess(Task<Void> voidTask) {
                Log.i(TAG, "SUCCESSFULLY ADDED NEW USER WITH UUID: " + user.getUid() + " TO DATABASE");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "FAILED TO ADD NEW USER WITH UUID: " + user.getUid() + " TO DATABASE");
                e.printStackTrace();
            }
        });
                 */
//
//        if (taskAddUserTodB.isSuccessful()) {
//            Log.i(TAG, "SUCCESSFULLY ADDED NEW USER WITH UUID: " + user.getUid() + " TO DATABASE");
//            addNewTaskModel(userModel);
//        } else if (taskAddUserTodB.isCanceled()) {
//            Log.e(TAG, "FAILED TO ADD NEW USER WITH UUID: " + user.getUid() + " TO DATABASE");
//        }
    }

    public Task<Void> addNewTaskModel(UserModel userModel) {
        TaskModel taskModel = new TaskModel(UUID.randomUUID().toString(), userModel.getUserID());
        Task<Void> addTaskModel = Database.DB_REFERENCE.child(Database.TASKS).child(userModel.getUserID()).setValue(taskModel);
        final ArrayList<Task<Void>> result = new ArrayList<Task<Void>>();
        return addTaskModel.continueWithTask(new Continuation<Void, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<Void> task) throws Exception {
                if (!task.isSuccessful()) {
                    task.getException().printStackTrace();
                }
                return addDefaultApplication(userModel);
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    task.getException().printStackTrace();
                }
                Log.i(TAG, "COMPLETED");
            }
        });
    }

    public Task<Void> addDefaultApplication(UserModel userModel) {
        DatabaseReference ref = Database.DB_REFERENCE.child(Database.APPLICATIONS).child(userModel.getUserID());
        String key = ref.push().getKey();
        ApplicationModel applicationModel = new ApplicationModel(UUID.randomUUID().toString(), "Default Application", Helper.getLongDateTime());
        applicationModel.setPushKey(key);
        return Database.DB_REFERENCE.child(Database.APPLICATIONS).child(userModel.getUserID()).child(applicationModel.getPushKey()).setValue(applicationModel);
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

    void reloadView() {
        fullName.getText().clear();
        email.getText().clear();
        password.getText().clear();

        fullName.clearFocus();
        email.clearFocus();
        password.clearFocus();
    }

    void proceedToDashboarForUser(FirebaseUser user) {
        Log.i(TAG, "PROCEED TO DASHBOARD");
        startActivity(new Intent(this, JourneyMain.class));
    }

    void signUpWithGoogle() {
        googleIntentLauncher.launch(new Intent(googleSignInClient.getSignInIntent()));
    }
}
