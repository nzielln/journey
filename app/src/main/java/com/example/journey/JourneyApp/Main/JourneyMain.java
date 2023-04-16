package com.example.journey.JourneyApp.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.journey.JourneyApp.Chat.ChatFragment;
import com.example.journey.JourneyApp.Dashboard.DashboardFragment;
import com.example.journey.JourneyApp.Dashboard.CreateNewPost;
import com.example.journey.JourneyApp.Insights.InsightsFragment;
import com.example.journey.JourneyApp.Login.LoginPage;
import com.example.journey.JourneyApp.Profile.Models.ProfileViewModel;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.JourneyApp.Profile.ProfileFragment;
import com.example.journey.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

public class JourneyMain extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private static final String TAG = JourneyMain.class.toGenericString();
    ProfileViewModel profileViewModel;
    private FragmentManager fragmentManager;
    GoogleSignInClient googleSignInClient;
    private BottomNavigationView tabBarNavigation;
    FirebaseUser currentUser = Database.FIREBASE_AUTH.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_journey_main);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Database.CLIENT_ID)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(JourneyMain.this, options);
        fragmentManager = getSupportFragmentManager();
        openDashboardFragment();

        tabBarNavigation = findViewById(R.id.tab_nav);
        tabBarNavigation.setOnItemSelectedListener(this);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        if (profileViewModel.getCurrentUserModel().getValue() == null) {
            Database.DB_REFERENCE.child(Database.USERS).child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        DataSnapshot results = task.getResult();
                        profileViewModel.updateCurrentUser(results.getValue(UserModel.class));
                        Log.i(TAG, "USER UPDATED");
                    } else {
                        Log.e(TAG, "SOMETHING WENT WRONG");
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            }).addOnCanceledListener(new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Log.e(TAG, "SOMETHING WENT TERRIBLY WRONG");
                }
            });
        }


    }

    public void openProfileFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ProfileFragment profileFragment = new ProfileFragment();

        transaction.replace(R.id.journey_fragment_container, profileFragment).commit();
    }
    public void openNewPostFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        CreateNewPost newPost = new CreateNewPost();

        transaction.replace(R.id.journey_fragment_container,newPost).commit();
    }

    public void openDashboardFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        DashboardFragment dashboardFragment = new DashboardFragment();

        transaction.replace(R.id.journey_fragment_container, dashboardFragment).commit();
    }

    public void openChatFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ChatFragment chatFragment = new ChatFragment();

        transaction.replace(R.id.journey_fragment_container, chatFragment).commit();
    }

    public void openInsightsFragment() {
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        InsightsFragment insightsFragment = new InsightsFragment();
//
//        transaction.replace(R.id.journey_fragment_container, insightsFragment).commit();

        Database.FIREBASE_AUTH.signOut();
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.i(TAG, "SIGNED OUT GOOGLE");
            }
        });

        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();

        switch (itemID) {
            case R.id.home_item:
                openDashboardFragment();
                break;
            case R.id.profile_item:
                openProfileFragment();
                break;
            case R.id.add_item:
                openNewPostFragment();
                break;
            case R.id.messages_item:
                openChatFragment();
                break;
            case R.id.insights_item:
                openInsightsFragment();
                break;
        }
        return true;
    }
}

