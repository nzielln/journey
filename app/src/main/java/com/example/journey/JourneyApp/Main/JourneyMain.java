package com.example.journey.JourneyApp.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.journey.JourneyApp.Chat.ChatFragment;
import com.example.journey.JourneyApp.Dashboard.DashboardFragment;
import com.example.journey.JourneyApp.Dashboard.CreateNewPost;
import com.example.journey.JourneyApp.Insights.InsightsFragment;
import com.example.journey.JourneyApp.Profile.ProfileFragment;
import com.example.journey.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class JourneyMain extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private FragmentManager fragmentManager;

    private BottomNavigationView tabBarNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_main);
//        setUpDatabase();

        fragmentManager = getSupportFragmentManager();
        openDashboardFragment();

        tabBarNavigation = findViewById(R.id.tab_nav);
        tabBarNavigation.setOnItemSelectedListener(this);
    }

//    public void setUpDatabase() {
//        Database.getDatabase(this);
//    }

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
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        InsightsFragment insightsFragment = new InsightsFragment();

        transaction.replace(R.id.journey_fragment_container, insightsFragment).commit();
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

