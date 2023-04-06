package com.example.journey.JourneyApp.Profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.journey.JourneyApp.Main.Database;
import com.example.journey.JourneyApp.Main.Helper;
import com.example.journey.JourneyApp.Profile.Modals.AddApplicationModal;
import com.example.journey.JourneyApp.Profile.Modals.AddTaskModal;
import com.example.journey.JourneyApp.Profile.Modals.UpdateApplicationModal;
import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.example.journey.R;
import com.example.journey.JourneyApp.Settings.SettingsFragment;
import com.example.journey.Sticker.Constants;
import com.example.journey.databinding.FragmentProfileBinding;
import com.example.journey.databinding.ProfileDetailsBinding;
import com.example.journey.databinding.ProfileTopMenuLayoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    String TAG = ProfileFragment.class.toGenericString();
    Button settingsTab;
    FragmentManager fragmentManager;
    TabLayout tabLayout;
    Button addNewApplication;
    FragmentProfileBinding binding;
    View layoutInflater;
    FirebaseUser currentUser;
    UserModel currentUserModel;

    ShapeableImageView profilePicture;
    TextView followers;
    TextView following;
    TextView userProfileName;
    /*
    String text = getString(R.string.FORMAT_STRING, ARGUMENTS);

     */

    // Listeners
    ValueEventListener userEventListener;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        fragmentManager = getChildFragmentManager();
        showFragment(new ProfileToDoFragment());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = binding.profileTab;
        addNewApplication = binding.addNewApplicationButton;
        tabLayout.addOnTabSelectedListener(this);

        ProfileTopMenuLayoutBinding topMenu = binding.includeProfileMenu;
        ProfileDetailsBinding profileDetailsBinding = binding.includeProfileDetails;

        profilePicture = profileDetailsBinding.profilePicture;
        followers = profileDetailsBinding.followersCount;
        following = profileDetailsBinding.followingCount;
        userProfileName = profileDetailsBinding.userProfileName;

        currentUser = Database.FIREBASE_AUTH.getCurrentUser();

        userEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUserModel = snapshot.getValue(UserModel.class);
                updateProfileView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };

//        Database.DB_REFERENCE.child(Database.USERS).child(currentUser.getUid()).addValueEventListener(userEventListener);
        Database.DB_REFERENCE.child(Database.USERS).child(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot results = task.getResult();
                    currentUserModel = results.getValue(UserModel.class);
                    updateProfileView();
                }
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickProfilePictureTapped();
            }
        });


        settingsTab = topMenu.settingsTabNav;
        settingsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsFragment();
            }
        });

        addNewApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddApplicationModal();
            }
        });
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.profile_fragment_container, fragment).commit();
    }

    public void openAddApplicationModal() {
        AddApplicationModal addApplicationModal = new AddApplicationModal();
        addApplicationModal.show(getChildFragmentManager(), AddApplicationModal.TAG);
    }

    public void openUpdateApplicationModal() {
        UpdateApplicationModal updateApplicationModal = new UpdateApplicationModal();
        updateApplicationModal.show(getChildFragmentManager(), UpdateApplicationModal.TAG);
    }

    public void openSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.settingsLayout, settingsFragment).commit();
    }

    public void updateProfileView() {

        String fullName = currentUserModel.getFirstName() + " " + currentUserModel.getLastName();
        userProfileName.setText(getString(R.string.user_name_format, fullName));
        following.setText(String.valueOf(currentUserModel.getFollowing()));
        followers.setText(String.valueOf(currentUserModel.getFollowers()));

        if (currentUserModel.getProfileImage() == null) {
            profilePicture.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.pick_photo));
        } else {
            StorageReference profileURL = Database.DB_STORAGE_REFERENCE.child("sample.jpeg");
            Glide.with(requireActivity()).load(profileURL).into(profilePicture);
        }

    }

    public void pickProfilePictureTapped() {
        ActivityResultLauncher<PickVisualMediaRequest> pickerLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
           if (uri != null) {
               Log.i(TAG, "SELECTED IMAGE WITH URI: " + uri);

           } else {
               Log.e(TAG, "NO IMAGE SELECTED");
           }
        });

        pickerLauncher.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build()
        );



    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                showFragment(new ProfileToDoFragment());
                break;
            case 1:
                showFragment(new ProfileTimelineFragment());
                break;
            case 2:
                showFragment(new ProfileDocumentsFragment());
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}